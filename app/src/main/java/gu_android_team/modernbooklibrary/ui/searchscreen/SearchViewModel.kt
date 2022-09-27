package gu_android_team.modernbooklibrary.ui.searchscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gu_android_team.modernbooklibrary.data.datasource.remote.DataState
import gu_android_team.modernbooklibrary.domain.Book
import gu_android_team.modernbooklibrary.domain.usecases.screens.SearchScreenUsecase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class SearchViewModel(private val usecase: SearchScreenUsecase) :
    ViewModel() {

    private val _searchedResultWhileTyping = MutableLiveData<DataState<List<Book>>>()
    val searchedResultWhileTyping: LiveData<DataState<List<Book>>>
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
        viewModelScope.launch {
            usecase.getSearchingBooksList(query, page).collect {
                _searchedResultWhileTyping.postValue(it as DataState<List<Book>>)
            }
        }
    }
}