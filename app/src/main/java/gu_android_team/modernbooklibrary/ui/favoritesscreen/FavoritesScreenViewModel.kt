package gu_android_team.modernbooklibrary.ui.favoritesscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gu_android_team.modernbooklibrary.domain.usecases.screens.FavoritesScreenUseCase
import gu_android_team.modernbooklibrary.utils.AppState
import kotlinx.coroutines.launch
import java.lang.Exception

class FavoritesScreenViewModel(
    private val usecase: FavoritesScreenUseCase
) : ViewModel() {

    private val _liveData = MutableLiveData<AppState>()
    val liveData: LiveData<AppState> = _liveData

    fun getData() {
        _liveData.value = AppState.AppStateLoading
        viewModelScope.launch {
            try {
                val data = usecase.getBooksInCache()
                _liveData.value = AppState.AppStateSuccess(data)
            } catch (e: Exception) {
                _liveData.value = AppState.AppStateError(e.message ?: "error")
            }
        }
    }
}