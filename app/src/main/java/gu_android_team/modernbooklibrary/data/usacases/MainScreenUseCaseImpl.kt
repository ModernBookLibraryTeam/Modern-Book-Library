package gu_android_team.modernbooklibrary.data.usacases

import gu_android_team.modernbooklibrary.data.datasource.remote.DataState
import gu_android_team.modernbooklibrary.domain.Repository
import gu_android_team.modernbooklibrary.domain.usecases.screens.MainScreenUseCase
import kotlinx.coroutines.flow.Flow

class MainScreenUseCaseImpl(
    private val repo: Repository

) : MainScreenUseCase {
    override suspend fun getNewBooksFromRemoteSource(): Flow<DataState<*>> =
        repo.getNewBooksFromRemoteDataSource()

    override suspend fun getSearchingBooksList(searchWord: String, page: String): Flow<DataState<*>> =
        repo.getSearchedBooksFromRemoteDataSource(searchWord, page)
}