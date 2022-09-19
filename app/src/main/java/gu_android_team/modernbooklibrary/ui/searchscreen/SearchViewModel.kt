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

const val TEXT_SEARCH_DELAY = 1000L
const val DELIMITER = ":"

class SearchViewModel(private val usecase: SearchScreenUsecase) :
    ViewModel() {

    private val _searchedResultWhileTyping = MutableLiveData<DataState<List<Book>>>()
    val searchedResultWhileTyping: LiveData<DataState<List<Book>>>
        get() = _searchedResultWhileTyping

    val textChangeStateFlow = MutableStateFlow("")

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

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    fun searchWordWhileTyping() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                textChangeStateFlow.debounce(TEXT_SEARCH_DELAY)
                    .filter {
                        return@filter it.isNotEmpty()
                    }
                    .distinctUntilChanged()
                    .flatMapLatest {
                        flow {
                            val list = it.split(DELIMITER)
                            usecase.getSearchingBooksList(list.first(), list.last()).collect {
                                emit(it)
                            }
                        }
                    }
            }.collect {
                _searchedResultWhileTyping.postValue(it as DataState<List<Book>>)
            }
        }
    }
}