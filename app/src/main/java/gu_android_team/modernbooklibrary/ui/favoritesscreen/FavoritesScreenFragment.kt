package gu_android_team.modernbooklibrary.ui.favoritesscreen

import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import gu_android_team.modernbooklibrary.R
import gu_android_team.modernbooklibrary.databinding.FragmentFavoritesScreenBinding
import gu_android_team.modernbooklibrary.domain.Screen

class FavoritesScreenFragment : Fragment(R.layout.fragment_favorites_screen), Screen {

    companion object {
        @JvmStatic
        fun newInstance() = FavoritesScreenFragment()
    }

    private val binding: FragmentFavoritesScreenBinding by viewBinding(
        FragmentFavoritesScreenBinding::bind
    )

    override fun showProgress() {
        with(binding) {
            favoritesProgressBar.visibility = View.VISIBLE
            favoritesEmptyImageView.visibility = View.GONE
            favoritesFilterFab.visibility = View.GONE
            favoritesListRecyclerView.visibility = View.GONE
        }
    }

    override fun showStandardScreen() {
        with(binding) {
            favoritesProgressBar.visibility = View.GONE
            favoritesEmptyImageView.visibility = View.VISIBLE
            favoritesFilterFab.visibility = View.VISIBLE
            favoritesListRecyclerView.visibility = View.VISIBLE
        }
    }

    override fun showError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.retry_string) {
                getFavoritesList()
            }
            .show()
    }

    private fun getFavoritesList() {
        TODO("Not yet implemented")
    }
}