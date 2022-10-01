package gu_android_team.modernbooklibrary.domain

import android.os.Bundle

interface NavigationController {
    fun openBookDescriptionScreen(bundle: Bundle)
    fun openBookDescriptionScreenFromSearchScreen(bundle: Bundle)
    fun openBookReaderScreen(bundle: Bundle)
}