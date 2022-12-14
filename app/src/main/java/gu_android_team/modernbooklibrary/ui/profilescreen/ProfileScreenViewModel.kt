package gu_android_team.modernbooklibrary.ui.profilescreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import gu_android_team.modernbooklibrary.domain.Book
import gu_android_team.modernbooklibrary.domain.User
import gu_android_team.modernbooklibrary.domain.usecases.screens.ProfileScreenUseCase
import gu_android_team.modernbooklibrary.utils.FIREBASE_DATABASE_USERS_TAG
import gu_android_team.modernbooklibrary.utils.FIREBASE_DATABASE_USER_FAVORITES_TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileScreenViewModel(private val usecase: ProfileScreenUseCase) : ViewModel() {
    private lateinit var auth: FirebaseAuth
    private val anonymousUser = User(name = "Anonymous", userPhotoUrl = null, isSigned = false)

    private val _livedataToObserve = MutableLiveData<User>()
    val livedataToObserve: LiveData<User> = _livedataToObserve

    fun getCurrentUser() {
        auth = Firebase.auth
        if (auth.currentUser != null) {
            _livedataToObserve.postValue(
                User(
                    name = auth.currentUser!!.displayName,
                    userPhotoUrl = auth.currentUser!!.photoUrl,
                    isSigned = true
                )
            )
        } else {
            _livedataToObserve.postValue(anonymousUser)
        }
    }

    fun authWithGoogle(idToken: String) {
        viewModelScope.launch {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            auth.signInWithCredential(credential).addOnCompleteListener { result ->

                if (result.isSuccessful) {
                    auth.currentUser?.let {
                        _livedataToObserve.postValue(
                            User(
                                name = auth.currentUser!!.displayName,
                                userPhotoUrl = auth.currentUser!!.photoUrl,
                                isSigned = true
                            )
                        )

                        synchronizeWithFirebaseDatabase(it)
                    }
                }
            }
        }
    }

    private fun synchronizeWithFirebaseDatabase(user: FirebaseUser) {
        viewModelScope.launch(Dispatchers.IO) {

            usecase.getBooksInCache().collect { localBookList ->
                val localFavoritesSet = mutableSetOf<Book>()
                localFavoritesSet.addAll(localBookList)

                Firebase.database.reference
                    .child(FIREBASE_DATABASE_USERS_TAG)
                    .child(user.uid)
                    .child(FIREBASE_DATABASE_USER_FAVORITES_TAG)
                    .get().addOnSuccessListener { dataSnapshot ->

                        val remoteList = mutableListOf<Book>()

                        dataSnapshot.children.forEach { item ->
                            val itemBook: Book = item.getValue(Book::class.java)!!
                            remoteList.add(itemBook)
                        }

                        localFavoritesSet.addAll(remoteList)

                        val updatedList = mutableListOf<Book>()
                        updatedList.addAll(localFavoritesSet)

                        Firebase.database.reference
                            .child(FIREBASE_DATABASE_USERS_TAG)
                            .child(user.uid)
                            .child(FIREBASE_DATABASE_USER_FAVORITES_TAG).setValue(
                                updatedList
                            )

                        if (localBookList.size != updatedList.size) {
                            updatedList.forEach { checkedBook ->
                                if (!localBookList.contains(checkedBook)) {
                                    usecase.addBookToLocalDb(checkedBook)
                                }
                            }
                        }
                    }
            }
        }
    }

    fun signOut() {
        auth.signOut()
        _livedataToObserve.postValue(anonymousUser)
    }
}