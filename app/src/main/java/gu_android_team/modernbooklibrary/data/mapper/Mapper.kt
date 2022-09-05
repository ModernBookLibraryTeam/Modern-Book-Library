package gu_android_team.modernbooklibrary.data.mapper

import gu_android_team.modernbooklibrary.data.datasource.local.LocalBook
import gu_android_team.modernbooklibrary.data.datasource.remote.BooksInfo
import gu_android_team.modernbooklibrary.data.datasource.remote.NewAndSearchBooksDTO
import gu_android_team.modernbooklibrary.data.datasource.remote.SpecificBookDTO
import gu_android_team.modernbooklibrary.domain.Book
import gu_android_team.modernbooklibrary.domain.mapper.Mapper

class Mapper : Mapper {
    override fun mapData(books: List<LocalBook>): List<Book> {
        return books.map {
            Book(
                id = it.id,
                title = it.title,
                subtitle = it.subtitle,
                author = it.author,
                isbn13 = it.isbn13,
                pages = it.pages,
                year = it.year,
                desc = it.description,
                image = it.image
            )
        }
    }

    override fun mapDataByTitle(book: LocalBook): Book {
        return Book(
            id = book.id,
            title = book.title,
            subtitle = book.subtitle,
            author = book.author,
            isbn13 = book.isbn13,
            pages = book.pages,
            year = book.year,
            desc = book.description,
            image = book.image
        )
    }

    override fun mapDomainToData(book: Book): LocalBook {
        return LocalBook(
            id = book.id,
            title = book.title,
            subtitle = book.subtitle,
            author = book.author,
            isbn13 = book.isbn13,
            pages = book.pages,
            year = book.year,
            description = book.desc,
            image = book.image
        )
    }

    override fun mapRemoteDataToLocal(books: List<BooksInfo>): List<Book> {
        return books.map {
            Book(
                title = it.title.toString(),
                subtitle = it.subtitle.toString(),
                isbn13 = it.isbn13.toString(),
                image = it.image.toString()
            )
        }
    }

    override fun mapRemoteDataSpecificToLocal(book: SpecificBookDTO?): Book {
        return Book(
            title = book?.title.toString(),
            subtitle = book?.subtitle.toString(),
            author = book?.authors.toString(),
            pages = book?.pages.toString(),
            year = book?.year.toString(),
            desc = book?.desc.toString(),
            image = book?.image.toString()
        )
    }
}