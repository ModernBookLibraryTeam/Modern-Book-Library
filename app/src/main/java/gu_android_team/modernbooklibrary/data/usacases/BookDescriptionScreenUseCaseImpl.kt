package gu_android_team.modernbooklibrary.data.usacases

import gu_android_team.modernbooklibrary.data.datasource.remote.DataState
import gu_android_team.modernbooklibrary.domain.Repository
import gu_android_team.modernbooklibrary.domain.usecases.screens.BookDescriptionScreenUseCase
import kotlinx.coroutines.flow.Flow

class BookDescriptionScreenUseCaseImpl(private val repo: Repository) :
    BookDescriptionScreenUseCase {
    override suspend fun getBookFromRemoteSource(bookIsbn13: String): Flow<DataState<*>> {
        return repo.getBookInfoFromRemoteDataSource(bookIsbn13)
    }
}