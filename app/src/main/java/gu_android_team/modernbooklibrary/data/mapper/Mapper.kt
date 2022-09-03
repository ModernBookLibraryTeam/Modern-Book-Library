package gu_android_team.modernbooklibrary.data.mapper

import gu_android_team.modernbooklibrary.data.datasource.local.LocalBook
import gu_android_team.modernbooklibrary.domain.Book
import gu_android_team.modernbooklibrary.domain.mapper.Mapper

class Mapper: Mapper {
    override fun mapData(books: List<LocalBook>): List<Book> {
        return books.map {
            Book(
                author = it.author,
                title = it.title,
                publishDate = it.publishDate,
                cover = it.bookJacket,
                id = it.id
            )
        }
    }

    override fun mapDataByTitle(book: LocalBook): Book {
        return Book(
            author = book.author,
            title = book.title,
            publishDate = book.publishDate,
            cover = book.bookJacket,
            id = book.id
        )
    }

    override fun mapDomainToData(book: Book): LocalBook {
        return LocalBook(
            id = book.id,
            title = book.title,
            author = book.author,
            publishDate = book.publishDate,
            bookJacket = book.cover
        )
    }
}