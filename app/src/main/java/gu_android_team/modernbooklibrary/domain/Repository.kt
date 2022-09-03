package gu_android_team.modernbooklibrary.domain

import gu_android_team.modernbooklibrary.data.datasource.remote.NewAndSearchBooksDTO
import gu_android_team.modernbooklibrary.data.datasource.remote.SpecificBookDTO
import retrofit2.Response

interface Repository {
    fun getNewBooksFromRemoteDataSource(callback: (Response<NewAndSearchBooksDTO>) -> Unit)
    fun getSearchedBooksFromRemoteDataSource(callback: (Response<NewAndSearchBooksDTO>) -> Unit)
    fun getBookInfoFromRemoteDataSource(callback: (Response<SpecificBookDTO>) -> Unit)
    fun getDataFromLocalDataSource(callback: (List<Book>) -> Unit)
    fun insertBookToDB(book: Book)
    fun deleteBookFromDB(book: Book)
}