package gu_android_team.modernbooklibrary.data.usacases

import gu_android_team.modernbooklibrary.data.datasource.remote.DataState
import gu_android_team.modernbooklibrary.domain.Book
import gu_android_team.modernbooklibrary.domain.Repository
import gu_android_team.modernbooklibrary.domain.usecases.screens.BookDescriptionScreenUseCase
import kotlinx.coroutines.flow.Flow

class BookDescriptionScreenUseCaseImpl(private val repo: Repository) :
    BookDescriptionScreenUseCase {
    override suspend fun getBookFromRemoteSource(bookIsbn13: String): Flow<DataState<*>> {
        return repo.getBookInfoFromRemoteDataSource(bookIsbn13)
    }

    override fun addBookToLocalDb(book: Book) {
        repo.insertBookToDB(book)
    }

    override suspend fun checkIfBookIsInDb(isbn13: String): Flow<Boolean> {
        return repo.isExistsDataFromLocalDataSource(isbn13)
    }

    override fun deleteBookById(book: Book) {
        repo.deleteBookFromDB(book)
    }
}