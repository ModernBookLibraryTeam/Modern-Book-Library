package gu_android_team.modernbooklibrary.data.datasource.local

import gu_android_team.modernbooklibrary.domain.Book
import gu_android_team.modernbooklibrary.domain.mapper.Mapper
import gu_android_team.modernbooklibrary.domain.LocalDataSource

class LocalDataSourceImpl(private val localProvider: BookDao, private val mapper: Mapper) : LocalDataSource<Book> {
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

    override suspend fun getDataSortedByDate(): List<Book> {
        return mapper.mapData(localProvider.getSortedBooksByDateAsc())
    }

    override suspend fun getDataSortedByDateDesc(): List<Book> {
        return mapper.mapData(localProvider.getSortedBooksByDateDesc())
    }

    override suspend fun getDataSortedByAuthor(): List<Book> {
        return mapper.mapData(localProvider.getSortedBooksByAuthorAsc())
    }

    override suspend fun getDataSortedByAuthorDesc(): List<Book> {
        return mapper.mapData(localProvider.getSortedBooksByAuthorDesc())
    }

    override suspend fun getDataSortedByName(): List<Book> {
        return mapper.mapData(localProvider.getSortedBooksByTitleAsc())
    }

    override suspend fun getDataSortedByNameDesc(): List<Book> {
        return mapper.mapData(localProvider.getSortedBooksByTitleDesc())
    }
}