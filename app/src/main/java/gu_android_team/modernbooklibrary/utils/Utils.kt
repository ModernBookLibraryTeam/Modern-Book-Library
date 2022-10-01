package gu_android_team.modernbooklibrary.utils

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.View
import android.view.inputmethod.InputMethodManager

const val ZERO_VAL = 0
const val ONE_VALUE = 1
const val FIREBASE_DATABASE_USERS_TAG = "users"
const val FIREBASE_DATABASE_USER_FAVORITES_TAG = "favoriteBooksList"
const val DEBUG_TAG = "@@@"

object KeyBoard {
    fun hideKeyboard(view: View, context: Context) {
        val inputMethodManager =
            context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}