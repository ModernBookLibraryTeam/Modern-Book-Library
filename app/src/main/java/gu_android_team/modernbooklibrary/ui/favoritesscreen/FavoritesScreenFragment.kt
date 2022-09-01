package gu_android_team.modernbooklibrary.ui.favoritesscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import gu_android_team.modernbooklibrary.R

class FavoritesScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites_screen, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = FavoritesScreenFragment()
    }
}