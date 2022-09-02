package gu_android_team.modernbooklibrary.ui.mainscreen

import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import gu_android_team.modernbooklibrary.R
import gu_android_team.modernbooklibrary.databinding.FragmentMainScreenBinding
import gu_android_team.modernbooklibrary.domain.Screen

class MainScreenFragment : Fragment(R.layout.fragment_main_screen), Screen {

    companion object {

        fun newInstance() = MainScreenFragment()
    }

    private val binding: FragmentMainScreenBinding by viewBinding(
        FragmentMainScreenBinding::bind
    )

    override fun showProgress() {
        with(binding) {
            mainProgressBar.visibility = View.VISIBLE
            mainContainerNestedScrollView.visibility = View.GONE
        }
    }

    override fun showStandardScreen() {
        with(binding) {
            mainProgressBar.visibility = View.GONE
            mainContainerNestedScrollView.visibility = View.VISIBLE
        }
    }

    override fun showError(message: String) {

        Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.retry_string) {
                updateData()
            }
            .show()
    }

    private fun updateData() {
        TODO("Not yet implemented")
    }
}