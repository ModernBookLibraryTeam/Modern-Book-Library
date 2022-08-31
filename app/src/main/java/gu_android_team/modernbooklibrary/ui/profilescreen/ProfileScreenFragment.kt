package gu_android_team.modernbooklibrary.ui.profilescreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import gu_android_team.modernbooklibrary.R

class ProfileScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile_screen, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() = ProfileScreenFragment()
    }
}