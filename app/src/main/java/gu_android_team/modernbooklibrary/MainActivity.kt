package gu_android_team.modernbooklibrary

import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputEditText
import gu_android_team.modernbooklibrary.databinding.ActivityMainBinding
import gu_android_team.modernbooklibrary.ui.bookdescriptionscreen.BookDescriptionFragment
import gu_android_team.modernbooklibrary.ui.favoritesscreen.FavoritesScreenFragment
import gu_android_team.modernbooklibrary.ui.mainscreen.MainScreenFragment
import gu_android_team.modernbooklibrary.ui.profilescreen.ProfileScreenFragment
import gu_android_team.modernbooklibrary.ui.searchscreen.SearchScreenFragment

class MainActivity : AppCompatActivity(), MainScreenFragment.MainScreenController {

    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)

    private lateinit var bottomNavigation: BottomNavigationView

    companion object {
        const val LAST_CHECKED_TIME_KEY = "LAST_CHECKED_TIME_KEY"
        const val APP_SHARED_PREFERENCES = "APP_SHARED_PREFERENCES"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.mainContainerFrameLayout, MainScreenFragment.newInstance())
                .commit()
        }

        bottomNavigation = binding.bottomNavigationView

        initBottomMenu()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action == MotionEvent.ACTION_DOWN) {
            val view = currentFocus

            if (view is TextInputEditText) {
                val outRect = Rect()
                view.getGlobalVisibleRect(outRect)
                if (!outRect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                    view.clearFocus()
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun initBottomMenu() {

        bottomNavigation.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.bottomMenuMainScreenButton -> {
                    supportFragmentManager.beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.mainContainerFrameLayout, MainScreenFragment.newInstance())
                        .addToBackStack("mainScreen")
                        .commit()

                    true
                }
                R.id.bottomMenuSearchScreenButton -> {
                    supportFragmentManager.beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.mainContainerFrameLayout, SearchScreenFragment.newInstance())
                        .addToBackStack("searchScreen")
                        .commit()

                    true
                }

                R.id.bottomMenuFavoritesScreenButton -> {
                    supportFragmentManager.beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(
                            R.id.mainContainerFrameLayout,
                            FavoritesScreenFragment.newInstance()
                        )
                        .addToBackStack("favoritesScreen")
                        .commit()

                    true
                }

                R.id.bottomMenuProfileScreenButton -> {
                    supportFragmentManager.beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.mainContainerFrameLayout, ProfileScreenFragment.newInstance())
                        .addToBackStack("profileScreen")
                        .commit()

                    true
                }

                else -> {
                    false
                }
            }
        }
    }

    override fun openBookDescriptionScreen(bundle: Bundle) {
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.mainContainerFrameLayout, BookDescriptionFragment.newInstance(bundle))
            .addToBackStack("bookDescriptionScreen")
            .commit()
    }
}
