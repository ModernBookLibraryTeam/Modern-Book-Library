package gu_android_team.modernbooklibrary.data.usacases

import gu_android_team.modernbooklibrary.domain.Book
import gu_android_team.modernbooklibrary.domain.Repository
import gu_android_team.modernbooklibrary.domain.usecases.screens.FavoritesScreenUseCase
import kotlinx.coroutines.flow.Flow

class FavoritesScreenUseCaseImpl(private val repo: Repository) : FavoritesScreenUseCase {

    override suspend fun getBooksInCache(): Flow<List<Book>> {
        return repo.getDataFromLocalDataSource()
    }

    override fun deleteBookById(idBook: String) {

    }
}