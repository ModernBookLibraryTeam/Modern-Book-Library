package gu_android_team.modernbooklibrary.ui.mainscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gu_android_team.modernbooklibrary.data.datasource.remote.DataState
import gu_android_team.modernbooklibrary.domain.Book
import gu_android_team.modernbooklibrary.domain.usecases.screens.MainScreenUseCase
import gu_android_team.modernbooklibrary.utils.AppState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainScreenViewModel(private val usecase: MainScreenUseCase) : ViewModel() {

    companion object {
        private const val FIRST_TITLE = "New Books"
        private const val FIRST_PAGE = "1"
        private const val SECOND_TITLE = "Android Books"
        private const val THIRD_TITLE = "Kotlin Books"
        private const val FOURTH_TITLE = "Compose Books"
    }

    private val _livedataToObserve = MutableLiveData<AppState>()
    val livedataToObserve: LiveData<AppState> = _livedataToObserve

    private val listOfLists = linkedMapOf<String, List<Book>>()

    fun getLists() {

        _livedataToObserve.postValue(AppState.AppStateLoading)
        viewModelScope.launch {

            val requestNewBooks = launch {
                getNewBooksList()
            }

            requestNewBooks.join()

            _livedataToObserve.postValue(AppState.AppStateSuccess(listOfLists))


            getSearchedBooksList(SECOND_TITLE, FIRST_PAGE)
            getSearchedBooksList(THIRD_TITLE, FIRST_PAGE)
            getSearchedBooksList(FOURTH_TITLE, FIRST_PAGE)
        }


    }

    private suspend fun getNewBooksList() {
        withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
            usecase.getNewBooksFromRemoteSource().collect { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        val data = dataState.data as List<Book>
                        addListToLinkedHashMap(data, FIRST_TITLE)
                    }

                    is DataState.Error -> {
                        _livedataToObserve.postValue(dataState.message?.let {
                            AppState.AppStateError(
                                it
                            )
                        })
                    }
                }
            }
        }
    }

    private suspend fun getSearchedBooksList(searchWord: String, page: String) {

        withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
            usecase.getSearchingBooksList(searchWord, page).collect { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        val data = dataState.data as List<Book>
                        addListToLinkedHashMap(data, searchWord)
                    }

                    is DataState.Error -> {
                        _livedataToObserve.postValue(dataState.message?.let {
                            AppState.AppStateError(
                                it
                            )
                        })
                    }
                }
            }
        }


    }

    private fun addListToLinkedHashMap(list: List<Book>, title: String) {
        listOfLists[title] = list
    }
}