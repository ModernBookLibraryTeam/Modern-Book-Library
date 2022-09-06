package gu_android_team.modernbooklibrary.domain

data class Book(
    val id: Int = 0,
    val title: String,
    val subtitle: String,
    val author: String = "",
    val publisher: String = "",
    val isbn10: String = "",
    val isbn13: String = "",
    val pages: String = "",
    val year: String = "",
    val desc: String = "",
    val image: String
)