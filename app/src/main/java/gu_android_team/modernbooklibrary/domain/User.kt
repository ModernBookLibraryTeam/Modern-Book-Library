package gu_android_team.modernbooklibrary.domain

import android.net.Uri

data class User(
    val name: String?,
    val userPhotoUrl: Uri?,
    val isSigned: Boolean
)