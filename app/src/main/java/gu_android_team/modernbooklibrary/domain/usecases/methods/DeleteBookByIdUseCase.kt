package gu_android_team.modernbooklibrary.domain.usecases.methods

import gu_android_team.modernbooklibrary.domain.Book

interface DeleteBookByIdUseCase {
    fun deleteBookById(book: Book)
}