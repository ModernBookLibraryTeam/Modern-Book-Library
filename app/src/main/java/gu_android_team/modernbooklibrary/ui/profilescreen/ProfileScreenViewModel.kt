package gu_android_team.modernbooklibrary.ui.profilescreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import gu_android_team.modernbooklibrary.domain.User
import kotlinx.coroutines.launch

class ProfileScreenViewModel : ViewModel() {
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