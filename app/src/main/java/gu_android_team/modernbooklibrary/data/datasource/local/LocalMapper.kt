package gu_android_team.modernbooklibrary.data.datasource.local

import gu_android_team.modernbooklibrary.domain.Book

interface LocalMapper {
    fun mapData(books:List<LocalBook>): List<Book>
    fun mapDataByTitle(book: LocalBook): Book
    fun mapDomainToData(book: Book): LocalBook
}