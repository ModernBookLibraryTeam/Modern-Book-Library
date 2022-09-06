package gu_android_team.modernbooklibrary.domain

import gu_android_team.modernbooklibrary.data.datasource.remote.DataSate
import kotlinx.coroutines.flow.Flow

interface Repository {
    val newBooksFromRemoteDataSource: Flow<DataSate<List<Book>>>
    val searchedBooksFromRemoteDataSource: Flow<DataSate<List<Book>>>
    val bookInfoFromRemoteDataSource: Flow<DataSate<Book>>
    fun getDataFromLocalDataSource(callback: (List<Book>) -> Unit)
    fun insertBookToDB(book: Book)
    fun deleteBookFromDB(book: Book)
}