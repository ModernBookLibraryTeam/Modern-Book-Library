package gu_android_team.modernbooklibrary.data.datasource.remote

data class NewAndSearchBooksDTO(
    val books: List<BooksInfo>
)

data class BooksInfo(
    val title: String?,
    val isbn13: String?,
    val image: String?
)