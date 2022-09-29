package gu_android_team.modernbooklibrary.data.datasource.local

import gu_android_team.modernbooklibrary.domain.Book
import gu_android_team.modernbooklibrary.domain.mapper.Mapper
import gu_android_team.modernbooklibrary.domain.LocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber

class LocalDataSourceImpl(private val localProvider: BookDao, private val mapper: Mapper) : LocalDataSource<Book> {
    override suspend fun getData(): Flow<List<Book>> {
        return localProvider.getAllBooks().map {
            mapper.mapLocalDataToAppData(it)
        }
    }

    override suspend fun getDataByTitle(title: String): Book {
        return mapper.mapLocalBookToBook(localProvider.getBooksByTitle(title))
    }

    override suspend fun insert(book: Book) {
        return localProvider.insertBook(mapper.mapBookToLocalBook(book))
    }

    override suspend fun delete(book: Book) {
        return localProvider.deleteById(book.isbn13)
    }

    override suspend fun isExistData(id: String): Boolean {
        return localProvider.isBookExist(id)
    }
}