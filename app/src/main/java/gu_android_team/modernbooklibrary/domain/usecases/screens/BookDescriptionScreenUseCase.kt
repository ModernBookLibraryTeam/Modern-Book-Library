package gu_android_team.modernbooklibrary.domain.usecases.screens

import gu_android_team.modernbooklibrary.domain.usecases.methods.*


interface BookDescriptionScreenUseCase : GetBookFromRemoteSourceUseCase, AddBookToLocalDbUseCase,
    CheckIfBookIsInDbUseCase, DeleteBookByIdUseCase, GetBooksInCache {
}