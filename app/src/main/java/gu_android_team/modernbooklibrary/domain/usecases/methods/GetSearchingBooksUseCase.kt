package gu_android_team.modernbooklibrary.domain.usecases.methods

import gu_android_team.modernbooklibrary.data.datasource.remote.DataState
import kotlinx.coroutines.flow.Flow

interface GetSearchingBooksUseCase {
    suspend fun getSearchingBooksList(searchWord: String, page: String): Flow<DataState<*>>
}