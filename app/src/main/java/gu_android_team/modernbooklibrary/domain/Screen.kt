package gu_android_team.modernbooklibrary.domain

interface Screen {
    fun showProgress()
    fun showStandardScreen()
    fun showError(message: String)
}