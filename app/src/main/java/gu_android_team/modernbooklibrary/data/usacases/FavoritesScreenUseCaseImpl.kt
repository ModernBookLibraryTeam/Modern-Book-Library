package gu_android_team.modernbooklibrary.data.usacases

import gu_android_team.modernbooklibrary.domain.Book
import gu_android_team.modernbooklibrary.domain.Repository
import gu_android_team.modernbooklibrary.domain.usecases.screens.FavoritesScreenUseCase

class FavoritesScreenUseCaseImpl(private val repo: Repository): FavoritesScreenUseCase {
    override suspend fun getBooksInCache(): List<Book> {
        return listOf(
            Book("isbn13", "title", "subtitle", "authors", "rating", "publisher",
            "isbn10", "pages", "year", "desc", "https://itbook.store/img/books/9781593278908.png"),
            Book("isbn13", "title", "subtitle", "authors", "rating", "publisher",
            "isbn10", "pages", "year", "desc", "https://itbook.store/img/books/9781593278908.png"),
            Book("isbn13", "title", "subtitle", "authors", "rating", "publisher",
            "isbn10", "pages", "year", "desc", "https://itbook.store/img/books/9781593278908.png"),
        )
    }
}