package gu_android_team.modernbooklibrary.domain

import gu_android_team.modernbooklibrary.data.datasource.remote.NewAndSearchBooksDTO
import gu_android_team.modernbooklibrary.data.datasource.remote.SpecificBookDTO
import gu_android_team.modernbooklibrary.utils.AppState
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Response

interface RemoteDataSource {
    val newBooks: Flow<List<Book>>
    val searchedBooks: Flow<List<Book>>
    val bookInfo: Flow<Book>
}