package gu_android_team.modernbooklibrary.domain.usecases.methods

import gu_android_team.modernbooklibrary.data.datasource.remote.DataState
import kotlinx.coroutines.flow.Flow

interface GetNewBooksUseCase {
    suspend fun getNewBooksFromRemoteSource(): Flow<DataState<*>>
}