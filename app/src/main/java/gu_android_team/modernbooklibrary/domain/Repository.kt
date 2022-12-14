package gu_android_team.modernbooklibrary.domain

import gu_android_team.modernbooklibrary.data.datasource.remote.DataState
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getNewBooksFromRemoteDataSource(): Flow<DataState<*>>
    suspend fun getSearchedBooksFromRemoteDataSource(
        searchWord: String,
        page: String
    ): Flow<DataState<List<Book>>>

    suspend fun getBookInfoFromRemoteDataSource(bookIsbn13: String): Flow<DataState<Book>>
    suspend fun getDataFromLocalDataSource(): Flow<List<Book>>
    fun isExistsDataFromLocalDataSource(id: String): Flow<Boolean>
    fun insertBookToDB(book: Book)
    fun deleteBookFromDB(book: Book)
}