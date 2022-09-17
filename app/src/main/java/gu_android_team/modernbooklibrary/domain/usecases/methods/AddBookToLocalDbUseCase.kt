package gu_android_team.modernbooklibrary.domain.usecases.methods

import gu_android_team.modernbooklibrary.domain.Book

interface AddBookToLocalDbUseCase {
    fun addBookToLocalDb(book: Book)
}