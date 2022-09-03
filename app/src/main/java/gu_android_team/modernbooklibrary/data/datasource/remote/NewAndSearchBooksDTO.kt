package gu_android_team.modernbooklibrary.data.datasource.remote

import com.google.gson.annotations.SerializedName

data class NewAndSearchBooksDTO(
    @SerializedName("books") val books: List<BooksInfo>
)

data class BooksInfo(
    @SerializedName("title") val title: String?,
    @SerializedName("isbn13") val isbn13: String?,
    @SerializedName("image") val image: String?
)