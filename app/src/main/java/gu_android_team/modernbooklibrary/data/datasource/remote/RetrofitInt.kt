package gu_android_team.modernbooklibrary.data.datasource.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitInt {
    @GET("1.0/new")
    fun getNewBooks(): Call<NewAndSearchBooksDTO>

    @GET("1.0/search/{query}")
    fun getBooksBySearching(
        @Path("query") request: String
    ): Call<NewAndSearchBooksDTO>

    @GET("1.0/books/{isbn13}")
    fun getBookInfo(
        @Path("isbn13") bookId: String
    ): Call<SpecificBookDTO>
}