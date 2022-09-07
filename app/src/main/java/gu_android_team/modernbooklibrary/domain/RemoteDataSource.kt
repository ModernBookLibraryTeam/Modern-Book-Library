package gu_android_team.modernbooklibrary.domain

import gu_android_team.modernbooklibrary.data.datasource.remote.DataState

interface RemoteDataSource {
    suspend fun getMappedNewBooksFromServer(): DataState<List<Book>>
    suspend fun getMappedBooksBySearchingFromServer(searchWord: String, page: String): DataState<List<Book>>
    suspend fun getMappedBookInfoFromServer(bookIsbn13: String): DataState<Book>
}