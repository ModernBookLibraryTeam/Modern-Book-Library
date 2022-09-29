package gu_android_team.modernbooklibrary.ui.favoritesscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import gu_android_team.modernbooklibrary.domain.Book
import gu_android_team.modernbooklibrary.domain.usecases.screens.FavoritesScreenUseCase
import gu_android_team.modernbooklibrary.utils.AppState
import gu_android_team.modernbooklibrary.utils.FIREBASE_DATABASE_USERS_TAG
import gu_android_team.modernbooklibrary.utils.FIREBASE_DATABASE_USER_FAVORITES_TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class FavoritesScreenViewModel(
    private val usecase: FavoritesScreenUseCase
) : ViewModel() {

    private val _liveData = MutableLiveData<AppState>()
    val liveData: LiveData<AppState> = _liveData

    val cashedBooksList = mutableListOf<Book>()

    fun getData() {
        _liveData.value = AppState.AppStateLoading
        viewModelScope.launch {
            try {
                usecase.getBooksInCache().flowOn(Dispatchers.Main)
                    .collect {
                        _liveData.value = AppState.AppStateSuccess(it)
                        cashedBooksList.addAll(it)
                    }
            } catch (e: Exception) {
                _liveData.value = AppState.AppStateError(e.message ?: "error")
            }
        }
    }

    fun deleteBookById(book: Book) {
        usecase.deleteBookById(book)

        cashedBooksList.removeAll { itemBook ->
            itemBook.isbn13 == book.isbn13
        }

        Firebase.database.reference
            .child(FIREBASE_DATABASE_USERS_TAG)
            .child(Firebase.auth.currentUser!!.uid)
            .child(FIREBASE_DATABASE_USER_FAVORITES_TAG).setValue(
                cashedBooksList
            )
    }
}