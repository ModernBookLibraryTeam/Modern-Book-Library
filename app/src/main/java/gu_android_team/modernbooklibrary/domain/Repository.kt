package gu_android_team.modernbooklibrary.domain

import gu_android_team.modernbooklibrary.data.datasource.remote.DataSate
import gu_android_team.modernbooklibrary.data.datasource.remote.NewAndSearchBooksDTO
import gu_android_team.modernbooklibrary.data.datasource.remote.SpecificBookDTO
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface Repository {
    val newBooksFromRemoteDataSource: Flow<DataSate<NewAndSearchBooksDTO>>
    val searchedBooksFromRemoteDataSource: Flow<DataSate<NewAndSearchBooksDTO>>
    val bookInfoFromRemoteDataSource: Flow<DataSate<SpecificBookDTO>>
    fun getDataFromLocalDataSource(callback: (List<Book>) -> Unit)
    fun insertBookToDB(book: Book)
    fun deleteBookFromDB(book: Book)
}