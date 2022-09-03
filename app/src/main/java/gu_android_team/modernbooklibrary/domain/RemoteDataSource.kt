package gu_android_team.modernbooklibrary.domain

import gu_android_team.modernbooklibrary.data.datasource.remote.NewAndSearchBooksDTO
import gu_android_team.modernbooklibrary.data.datasource.remote.SpecificBookDTO
import kotlinx.coroutines.flow.Flow
import retrofit2.Call

interface RemoteDataSource {
    val newBooks: Flow<Call<NewAndSearchBooksDTO>>
    val searchedBooks: Flow<Call<NewAndSearchBooksDTO>>
    val nextPageOfSearchedBooks: Flow<Call<NewAndSearchBooksDTO>>
    val bookInfo: Flow<Call<SpecificBookDTO>>
}