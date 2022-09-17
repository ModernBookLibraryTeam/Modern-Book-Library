package gu_android_team.modernbooklibrary

import android.animation.ObjectAnimator
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputEditText
import gu_android_team.modernbooklibrary.databinding.ActivityMainBinding
import gu_android_team.modernbooklibrary.ui.mainscreen.MainScreenFragment
import gu_android_team.modernbooklibrary.utils.ZERO_VAL

class MainActivity : AppCompatActivity(), MainScreenFragment.MainScreenController {

    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)

    private lateinit var bottomNavigation: BottomNavigationView
    private var keepSplashOnScreen = true

    companion object {
        const val LAST_CHECKED_TIME_KEY = "LAST_CHECKED_TIME_KEY"
        const val APP_SHARED_PREFERENCES = "APP_SHARED_PREFERENCES"
        const val SLIDE_LEFT_ANIMATION_DURATION = 1000L
        const val SLIDE_LEFT_ANIMATION_START_POINT = 0f
    }

    private lateinit var navController: NavController
    private lateinit var splashScreen: SplashScreen

    override fun onCreate(savedInstanceState: Bundle?) {

        if (savedInstanceState == null) {
            splashScreen = installSplashScreen()
            splashScreen.setKeepOnScreenCondition { keepSplashOnScreen }
            initSplashScreenAnimationListener()
        } else {
            setTheme(R.style.Theme_ModernBookLibrary)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigation = binding.bottomNavigationView

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.mainContainerFrameLayout) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(bottomNavigation, navController)
    }

    private fun initSplashScreenAnimationListener() {
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            val slideLeft = ObjectAnimator.ofFloat(
                splashScreenView.view,
                View.TRANSLATION_X,
                SLIDE_LEFT_ANIMATION_START_POINT,
                -splashScreenView.view.width.toFloat()
            )

            slideLeft.duration = SLIDE_LEFT_ANIMATION_DURATION

            slideLeft.doOnEnd { splashScreenView.remove() }

            slideLeft.start()
        }
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
                    imm.hideSoftInputFromWindow(view.getWindowToken(), ZERO_VAL)
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun openBookDescriptionScreen(bundle: Bundle) {

        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.bottomMenuMainScreenButton, false)
            .setLaunchSingleTop(true)
            .build()

        navController.navigate(R.id.bookDescriptionScreen, bundle, navOptions)
    }

    fun setKeepOnScreenCondition(state: Boolean) {
        keepSplashOnScreen = state
    }
}
