package gu_android_team.modernbooklibrary.data.mapper

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import gu_android_team.modernbooklibrary.data.datasource.local.LocalBook
import gu_android_team.modernbooklibrary.data.datasource.remote.BooksInfo
import gu_android_team.modernbooklibrary.data.datasource.remote.SpecificBookDTO
import gu_android_team.modernbooklibrary.domain.Book
import gu_android_team.modernbooklibrary.domain.mapper.Mapper

class MapperImpl : Mapper {

    companion object {
        private const val NO_IMAGE_URL =
            "https://drive.google.com/file/d/1Ghs1TfWDwI-ftTr7D07bOxW8lO3jXbmM/view?usp=sharing"
    }

    override fun mapLocalDataToAppData(localBooksList: List<LocalBook>): List<Book> {
        return localBooksList.map {
            Book(
                title = it.title,
                subtitle = it.subtitle,
                authors = it.author,
                publisher = it.publisher,
                isbn10 = it.isbn10,
                isbn13 = it.isbn13,
                pages = it.pages,
                rating = it.rating,
                year = it.year,
                desc = it.description,
                image = it.image,
                pdfLinksList = null
            )
        }
    }

    override fun mapLocalBookToBook(localBook: LocalBook): Book {
        return Book(
            title = localBook.title,
            subtitle = localBook.subtitle,
            authors = localBook.author,
            publisher = localBook.publisher,
            isbn10 = localBook.isbn10,
            isbn13 = localBook.isbn13,
            pages = localBook.pages,
            rating = localBook.rating,
            year = localBook.year,
            desc = localBook.description,
            image = localBook.image,
            pdfLinksList = null
        )
    }

    override fun mapBookToLocalBook(book: Book): LocalBook {
        return LocalBook(
            title = book.title,
            subtitle = book.subtitle,
            author = book.authors,
            publisher = book.publisher,
            isbn10 = book.isbn10,
            isbn13 = book.isbn13,
            pages = book.pages,
            rating = book.rating,
            year = book.year,
            description = book.desc,
            image = book.image
        )
    }

    override fun mapRemoteDataToAppData(booksListDTO: List<BooksInfo>): List<Book> {
        return booksListDTO.map {
            Book(
                title = it.title.toString(),
                subtitle = it.subtitle.toString(),
                isbn13 = it.isbn13.toString(),
                image = it.image.toString(),
                pdfLinksList = null
            )
        }
    }

    override fun mapRemoteSpecificBookDtoToBook(bookDTO: SpecificBookDTO?): Book {
        return Book(
            title = bookDTO?.title ?: "",
            subtitle = bookDTO?.subtitle ?: "",
            authors = bookDTO?.authors ?: "",
            publisher = bookDTO?.publisher ?: "",
            isbn10 = bookDTO?.isbn10 ?: "",
            isbn13 = bookDTO?.isbn13 ?: "",
            pages = bookDTO?.pages ?: "",
            rating = bookDTO?.rating ?: "",
            year = bookDTO?.year ?: "",
            desc = bookDTO?.desc ?: "",
            image = bookDTO?.image ?: NO_IMAGE_URL,
            pdfLinksList = Gson().fromJson<HashMap<String, String>>(bookDTO?.pdfLinksList)
        )
    }

    private inline fun <reified T> Gson.fromJson(json: JsonObject?): T = fromJson<T>(json, object: TypeToken<T>() {}.type)
}