package gu_android_team.modernbooklibrary.ui.searchscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gu_android_team.modernbooklibrary.data.datasource.remote.DataState
import gu_android_team.modernbooklibrary.domain.Book
import gu_android_team.modernbooklibrary.domain.usecases.screens.SearchScreenUsecase
import gu_android_team.modernbooklibrary.utils.AppState
import kotlinx.coroutines.launch

class SearchViewModel(private val usecase: SearchScreenUsecase) :
    ViewModel() {
     val listOfSearchedBooks = mutableListOf<Book>()
    private val _searchedResultWhileTyping = MutableLiveData<AppState>()
    val searchedResultWhileTyping: LiveData<AppState>
        get() = _searchedResultWhileTyping

    private val _searchedResult = MutableLiveData<DataState<List<Book>>>()
    val searchedResult: LiveData<DataState<List<Book>>>
        get() = _searchedResult

    fun getSearchedBooks(query: String, page: String) {
        viewModelScope.launch {
            usecase.getSearchingBooksList(query, page).collect {
                _searchedResult.postValue(it as DataState<List<Book>>)
            }
        }
    }

    fun getFirstSearchedBooks(query: String, page: String) {
        _searchedResultWhileTyping.postValue(AppState.AppStateLoading)
        viewModelScope.launch {
            usecase.getSearchingBooksList(query, page).collect {
                when (it) {
                    is DataState.Success -> {
                        listOfSearchedBooks.clear()
                        listOfSearchedBooks.addAll(it.data as List<Book>)
                        _searchedResultWhileTyping.postValue(
                            AppState.AppStateSuccess(
                                listOfSearchedBooks
                            )
                        )
                    }
                    is DataState.Error -> it.message?.let { it1 -> AppState.AppStateError(it1) }
                }

            }
        }
    }
}