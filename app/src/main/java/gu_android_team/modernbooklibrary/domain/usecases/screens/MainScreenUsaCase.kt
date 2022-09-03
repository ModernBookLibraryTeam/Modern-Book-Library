package gu_android_team.modernbooklibrary.domain.usecases.screens

import gu_android_team.modernbooklibrary.domain.usecases.methods.GetFakeBooksListUsaCase
import gu_android_team.modernbooklibrary.domain.usecases.methods.GetNewBooksUseCase

interface MainScreenUsaCase: GetNewBooksUseCase, GetFakeBooksListUsaCase {
}
