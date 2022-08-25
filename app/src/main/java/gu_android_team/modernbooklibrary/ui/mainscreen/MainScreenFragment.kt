package gu_android_team.modernbooklibrary.ui.mainscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import gu_android_team.modernbooklibrary.R

class MainScreenFragment : Fragment(R.layout.fragment_main_screen) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    companion object {

        fun newInstance() = MainScreenFragment()
    }
}