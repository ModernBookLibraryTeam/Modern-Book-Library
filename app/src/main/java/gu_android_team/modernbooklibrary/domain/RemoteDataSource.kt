package gu_android_team.modernbooklibrary.domain

import gu_android_team.modernbooklibrary.data.datasource.remote.DataSate
import gu_android_team.modernbooklibrary.data.datasource.remote.NewAndSearchBooksDTO
import gu_android_team.modernbooklibrary.data.datasource.remote.SpecificBookDTO
import gu_android_team.modernbooklibrary.utils.AppState
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Response

interface RemoteDataSource {
   suspend fun getNewBooksFromServer() : DataSate<NewAndSearchBooksDTO>
   suspend fun getBooksBySearchingFromServer() : DataSate<NewAndSearchBooksDTO>
   suspend fun getBookInfoFromServer():DataSate<SpecificBookDTO>
}