package gu_android_team.modernbooklibrary.data.datasource.remote

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitInt {
    @GET("1.0/new")
    suspend fun getNewBooks(): Response<NewAndSearchBooksDTO>

    @GET("1.0/search/{query}/{page}")
    fun getBooksBySearching(
        @Path("query") request: String,
        @Path("page") page: String
    ): Response<NewAndSearchBooksDTO>

    @GET("1.0/books/{isbn13}")
    fun getBookInfo(
        @Path("isbn13") bookId: String
    ): Response<SpecificBookDTO>
}