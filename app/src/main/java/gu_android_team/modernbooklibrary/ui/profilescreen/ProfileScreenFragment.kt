package gu_android_team.modernbooklibrary.ui.profilescreen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.squareup.picasso.Picasso
import gu_android_team.modernbooklibrary.R
import gu_android_team.modernbooklibrary.databinding.FragmentProfileScreenBinding
import gu_android_team.modernbooklibrary.di.PROFILE_SCREEN_VIEW_MODEL
import gu_android_team.modernbooklibrary.utils.DEBUG_TAG
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import timber.log.Timber

class ProfileScreenFragment : Fragment(R.layout.fragment_profile_screen) {

    val binding: FragmentProfileScreenBinding by viewBinding(FragmentProfileScreenBinding::bind)
    private lateinit var launcher: ActivityResultLauncher<Intent>
    private val profileViewModel: ProfileScreenViewModel by viewModel(
        named(
            PROFILE_SCREEN_VIEW_MODEL
        )
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToLiveData()
        initLauncher()
        profileViewModel.getCurrentUser()
        initOnClickListeners()
    }

    private fun subscribeToLiveData() {
        profileViewModel.livedataToObserve.observe(viewLifecycleOwner) { user ->
            fillUserInfo(user.name, user.userPhotoUrl, user.isSigned)
        }
    }

    private fun initLauncher() {
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)

            try {
                val account = task.getResult(ApiException::class.java)
                account.idToken?.let { idToken ->
                    profileViewModel.authWithGoogle(idToken)
                }

            } catch (e: ApiException) {
                Timber.tag(DEBUG_TAG).d(e.localizedMessage)
            }
        }
    }

    private fun initOnClickListeners() {
        binding.profileSignInButton.setOnClickListener {
            signInWithGoogle()
        }

        binding.profileSignOutButton.setOnClickListener {
            profileViewModel.signOut()
        }
    }

    private fun getGoogleClient(): GoogleSignInClient {
        val client = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(requireActivity(), client)
    }

    private fun signInWithGoogle() {
        val client = getGoogleClient()
        launcher.launch(client.signInIntent)
    }

    private fun swapSignButtons(signStatus: Boolean) {

        if (signStatus) {
            binding.profileSignInButton.visibility = View.GONE
            binding.profileSignOutButton.visibility = View.VISIBLE
        } else {
            binding.profileSignInButton.visibility = View.VISIBLE
            binding.profileSignOutButton.visibility = View.GONE
        }
    }

    private fun fillUserInfo(userName: String?, photo: Uri?, isSigned: Boolean) {
        if (isSigned) {
            userName?.let { binding.profileUserNameLabel.text = userName }
            photo?.let {
                Picasso.get()
                    .load(photo)
                    .placeholder(R.drawable.ic_baseline_person_pin_24)
                    .into(binding.profileUserImageView)
            }
        } else {
            binding.profileUserNameLabel.text = getString(R.string.anonymous_label)
            Picasso.get()
                .load(R.drawable.ic_baseline_person_pin_24)
                .placeholder(R.drawable.ic_baseline_person_pin_24)
                .into(binding.profileUserImageView)
        }

        swapSignButtons(isSigned)
    }
}