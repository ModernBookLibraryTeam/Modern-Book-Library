package gu_android_team.modernbooklibrary.domain.mapper

import gu_android_team.modernbooklibrary.data.datasource.remote.BooksInfo
import gu_android_team.modernbooklibrary.data.datasource.remote.SpecificBookDTO
import gu_android_team.modernbooklibrary.domain.Book

interface RemoteMapper {
    fun mapRemoteDataToLocal(books: List<BooksInfo>): List<Book>
    fun mapRemoteDataSpecificToLocal(book: SpecificBookDTO): Book
}