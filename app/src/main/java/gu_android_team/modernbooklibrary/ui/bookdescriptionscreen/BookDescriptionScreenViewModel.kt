package gu_android_team.modernbooklibrary.ui.bookdescriptionscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gu_android_team.modernbooklibrary.data.datasource.remote.DataState
import gu_android_team.modernbooklibrary.domain.Book
import gu_android_team.modernbooklibrary.domain.usecases.screens.BookDescriptionScreenUseCase
import gu_android_team.modernbooklibrary.utils.AppState
import kotlinx.coroutines.launch

class BookDescriptionScreenViewModel(
    private val usecase: BookDescriptionScreenUseCase
) : ViewModel() {

    private val _livedataToObserve = MutableLiveData<AppState>()
    val livedataToObserve: LiveData<AppState> = _livedataToObserve

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
}