package gu_android_team.modernbooklibrary.data.datasource.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInt {
    @GET("/new")
    fun getNewBooks(): Call<NewAndSearchBooksDTO>

    @GET("/search")
    fun getBooksBySearching(
        @Query("query") request: String
    ): Call<NewAndSearchBooksDTO>

    @GET("/books")
    fun getBookInfo(
        @Query("isbn13") bookId: String
    ): Call<SpecificBookDTO>
}