package gu_android_team.modernbooklibrary.data.datasource

import gu_android_team.modernbooklibrary.domain.Book
import gu_android_team.modernbooklibrary.data.datasource.local.BookDao
import gu_android_team.modernbooklibrary.data.datasource.local.LocalMapper
import gu_android_team.modernbooklibrary.domain.LocalDataSource

class LocalDataSourceImpl(private val localProvider: BookDao, private val mapper: LocalMapper) : LocalDataSource<Book> {
    override suspend fun getData(): List<Book> {
        return mapper.mapData(localProvider.getAllBooks())
    }

    override suspend fun getDataByTitle(title: String): Book {
        return mapper.mapDataByTitle(localProvider.getBooksByTitle(title))
    }

    override suspend fun insert(book: Book) {
        return localProvider.insertBook(mapper.mapDomainToData(book))
    }

    override suspend fun delete(book: Book) {
        return localProvider.deleteBook(mapper.mapDomainToData(book))
    }
}