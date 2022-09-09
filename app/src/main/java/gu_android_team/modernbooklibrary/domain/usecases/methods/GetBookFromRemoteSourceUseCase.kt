package gu_android_team.modernbooklibrary.domain.usecases.methods

import gu_android_team.modernbooklibrary.data.datasource.remote.DataState
import kotlinx.coroutines.flow.Flow

interface GetBookFromRemoteSourceUseCase {
    suspend fun getBookFromRemoteSource(bookIsbn13: String): Flow<DataState<*>>
}