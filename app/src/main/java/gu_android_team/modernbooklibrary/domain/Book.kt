package gu_android_team.modernbooklibrary.domain

data class Book(
    val isbn13: String = "",
    val title: String = "",
    val subtitle: String = "",
    val authors: String = "",
    val rating: String = "",
    val publisher: String = "",
    val isbn10: String = "",
    val pages: String = "",
    val year: String = "",
    val desc: String = "",
    val image: String = "",
    val pdfLinksList: HashMap<String, String>? = null
)