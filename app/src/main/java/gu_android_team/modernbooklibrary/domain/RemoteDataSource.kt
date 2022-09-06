package gu_android_team.modernbooklibrary.domain

import gu_android_team.modernbooklibrary.data.datasource.remote.DataSate
import retrofit2.Response

interface RemoteDataSource {
    suspend fun getMappedNewBooksFromServer(): DataSate<List<Book>>
    suspend fun getMappedBooksBySearchingFromServer(): DataSate<List<Book>>
    suspend fun getMappedBookInfoFromServer(): DataSate<Book>
}