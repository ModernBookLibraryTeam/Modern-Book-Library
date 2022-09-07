package gu_android_team.modernbooklibrary.data.datasource.local

import gu_android_team.modernbooklibrary.domain.Book
import gu_android_team.modernbooklibrary.domain.mapper.Mapper
import gu_android_team.modernbooklibrary.domain.LocalDataSource

class LocalDataSourceImpl(private val localProvider: BookDao, private val mapper: Mapper) : LocalDataSource<Book> {
    override suspend fun getData(): List<Book> {
        return mapper.mapLocalDataToAppData(localProvider.getAllBooks())
    }

    override suspend fun getDataByTitle(title: String): Book {
        return mapper.mapLocalBookToBook(localProvider.getBooksByTitle(title))
    }

    override suspend fun insert(book: Book) {
        return localProvider.insertBook(mapper.mapBookToLocalBook(book))
    }

    override suspend fun delete(book: Book) {
        return localProvider.deleteBook(mapper.mapBookToLocalBook(book))
    }
}