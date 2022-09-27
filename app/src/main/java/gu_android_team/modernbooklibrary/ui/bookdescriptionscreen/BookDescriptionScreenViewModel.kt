package gu_android_team.modernbooklibrary.ui.bookdescriptionscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import gu_android_team.modernbooklibrary.data.datasource.remote.DataState
import gu_android_team.modernbooklibrary.domain.Book
import gu_android_team.modernbooklibrary.domain.usecases.screens.BookDescriptionScreenUseCase
import gu_android_team.modernbooklibrary.utils.AppState
import gu_android_team.modernbooklibrary.utils.FIREBASE_DATABASE_USERS_TAG
import gu_android_team.modernbooklibrary.utils.FIREBASE_DATABASE_USER_FAVORITES_TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
                _livedataForCheckFavorites.postValue(it)
            }
        }
    }

    fun addBookToFavorites(book: Book) {
        usecase.addBookToLocalDb(book)
        updateFavoritesInFirebaseDatabase(book, false)

    }

    fun deleteBookFromFavorites(book: Book) {
        usecase.deleteBookById(book)
        updateFavoritesInFirebaseDatabase(book, true)
    }

    private fun updateFavoritesInFirebaseDatabase(book: Book, isDeleted: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {

            usecase.getBooksInCache().collect {
                val localFavoritesSet = mutableSetOf<Book>()
                localFavoritesSet.addAll(it)

                if (Firebase.auth.currentUser != null) {

                    Firebase.database.reference
                        .child(FIREBASE_DATABASE_USERS_TAG)
                        .child(Firebase.auth.currentUser!!.uid)
                        .child(FIREBASE_DATABASE_USER_FAVORITES_TAG)
                        .get().addOnSuccessListener { dataSnapshot ->

                            val remoteList = mutableListOf<Book>()

                            dataSnapshot.children.forEach { item ->
                                val itemBook: Book = item.getValue(Book::class.java)!!
                                remoteList.add(itemBook)
                            }

                            localFavoritesSet.addAll(remoteList)

                            if (isDeleted) {
                                localFavoritesSet.removeAll { sourcedBook ->
                                    sourcedBook.isbn13 == book.isbn13
                                }
                            }

                            val updatedList = mutableListOf<Book>()
                            updatedList.addAll(localFavoritesSet)

                            Firebase.database.reference
                                .child(FIREBASE_DATABASE_USERS_TAG)
                                .child(Firebase.auth.currentUser!!.uid)
                                .child(FIREBASE_DATABASE_USER_FAVORITES_TAG).setValue(
                                    updatedList
                                )
                        }
                }
            }
        }
    }
}