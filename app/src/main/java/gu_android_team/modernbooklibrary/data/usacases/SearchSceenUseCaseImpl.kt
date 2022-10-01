package gu_android_team.modernbooklibrary.data.usacases

import gu_android_team.modernbooklibrary.data.datasource.remote.DataState
import gu_android_team.modernbooklibrary.data.repository.RepositoryImpl
import gu_android_team.modernbooklibrary.domain.Repository
import gu_android_team.modernbooklibrary.domain.usecases.screens.SearchScreenUsecase
import kotlinx.coroutines.flow.Flow

class SearchScreenUseCaseImpl(private val repo: Repository) : SearchScreenUsecase {
    override suspend fun getSearchingBooksList(
        searchWord: String,
        page: String
    ): Flow<DataState<*>> = repo.getSearchedBooksFromRemoteDataSource(searchWord, page)
}