package gu_android_team.modernbooklibrary.domain.usecases.methods

import gu_android_team.modernbooklibrary.domain.Book
import kotlinx.coroutines.flow.Flow

interface GetBooksInCache {
    suspend fun getBooksInCache(): Flow<List<Book>>
}