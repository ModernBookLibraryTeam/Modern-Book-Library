package gu_android_team.modernbooklibrary.domain.mapper

import gu_android_team.modernbooklibrary.data.datasource.local.LocalBook
import gu_android_team.modernbooklibrary.domain.Book

interface LocalMapper {

    fun mapLocalDataToAppData(localBooksList: List<LocalBook>): List<Book>
    fun mapLocalBookToBook(localBook: LocalBook): Book
    fun mapBookToLocalBook(book: Book): LocalBook
}