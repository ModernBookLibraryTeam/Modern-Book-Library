package gu_android_team.modernbooklibrary.ui.searchscreen

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gu_android_team.modernbooklibrary.data.datasource.remote.DataState
import gu_android_team.modernbooklibrary.data.datasource.remote.NewAndSearchBooksDTO
import gu_android_team.modernbooklibrary.domain.Book
import gu_android_team.modernbooklibrary.domain.usecases.screens.SearchScreenUsecase
import gu_android_team.modernbooklibrary.utils.AppState
import kotlinx.coroutines.launch
import retrofit2.http.Query

class SearchViewModel(private val usecase: SearchScreenUsecase) :
    ViewModel() {
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
}