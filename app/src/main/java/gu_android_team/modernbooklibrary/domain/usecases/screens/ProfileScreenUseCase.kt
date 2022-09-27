package gu_android_team.modernbooklibrary.domain.usecases.screens

import gu_android_team.modernbooklibrary.domain.usecases.methods.AddBookToLocalDbUseCase
import gu_android_team.modernbooklibrary.domain.usecases.methods.GetBooksInCache

interface ProfileScreenUseCase : GetBooksInCache, AddBookToLocalDbUseCase {
}