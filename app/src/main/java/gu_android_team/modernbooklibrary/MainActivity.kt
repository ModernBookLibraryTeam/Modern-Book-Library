package gu_android_team.modernbooklibrary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import gu_android_team.modernbooklibrary.ui.mainscreen.MainScreenFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.mainContainerFrameLayout, MainScreenFragment.newInstance())
                .commit()
        }
    }
}

