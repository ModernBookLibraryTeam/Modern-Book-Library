package gu_android_team.modernbooklibrary.data.usacases

import gu_android_team.modernbooklibrary.domain.Book
import gu_android_team.modernbooklibrary.domain.Repository
import gu_android_team.modernbooklibrary.domain.usecases.screens.ProfileScreenUseCase
import kotlinx.coroutines.flow.Flow

class ProfileScreenUseCaseImpl(private val repo: Repository) : ProfileScreenUseCase {
    override suspend fun getBooksInCache(): Flow<List<Book>> {
        return repo.getDataFromLocalDataSource()
    }

    override fun addBookToLocalDb(book: Book) {
        repo.insertBookToDB(book)
    }
}