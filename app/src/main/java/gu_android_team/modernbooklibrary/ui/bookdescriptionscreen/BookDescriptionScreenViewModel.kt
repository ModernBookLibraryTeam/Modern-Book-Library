package gu_android_team.modernbooklibrary.ui.bookdescriptionscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gu_android_team.modernbooklibrary.data.datasource.remote.DataState
import gu_android_team.modernbooklibrary.domain.Book
import gu_android_team.modernbooklibrary.domain.usecases.screens.BookDescriptionScreenUseCase
import gu_android_team.modernbooklibrary.utils.AppState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class BookDescriptionScreenViewModel(
    private val usecase: BookDescriptionScreenUseCase
) : ViewModel() {

    private val _livedataToObserve = MutableLiveData<AppState>()
    val livedataToObserve: LiveData<AppState> = _livedataToObserve

    private val _livedataForCheckFavorites = MutableLiveData<Boolean>()
    val livedataForCheckFavorites: LiveData<Boolean> = _livedataForCheckFavorites

    fun getBookInfo(bookIsbn13: String) {
        _livedataToObserve.postValue(AppState.AppStateLoading)

        viewModelScope.launch {

            usecase.getBookFromRemoteSource(bookIsbn13).collect { bookData ->
                when (bookData) {
                    is DataState.Success<*> -> {
                        val bookInfo = bookData.data as Book
                        _livedataToObserve.postValue(AppState.AppStateSuccess(bookInfo))
                    }

                    is DataState.Error -> {
                        _livedataToObserve.postValue(bookData.message?.let { errorMessage ->
                            AppState.AppStateError(
                                errorMessage
                            )
                        })
                    }
                }
            }
        }
    }

    fun checkIfBookIsInLocalDb(isbn13: String) {

        viewModelScope.launch(Dispatchers.IO) {
            usecase.checkIfBookIsInDb(isbn13).collect {
                Timber.tag("@@@").d(it.toString())
                _livedataForCheckFavorites.postValue(it)
            }
        }
    }

    fun addBookToFavorites(book: Book) {
        usecase.addBookToLocalDb(book)
    }

    fun deleteBookFromFavorites(book: Book) {
        usecase.deleteBookById(book)
    }
}