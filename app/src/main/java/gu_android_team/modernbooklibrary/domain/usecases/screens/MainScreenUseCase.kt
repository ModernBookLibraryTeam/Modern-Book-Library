package gu_android_team.modernbooklibrary.domain.usecases.screens

import gu_android_team.modernbooklibrary.domain.usecases.methods.GetNewBooksUseCase
import gu_android_team.modernbooklibrary.domain.usecases.methods.GetSearchingBooksUseCase

interface MainScreenUseCase : GetNewBooksUseCase, GetSearchingBooksUseCase {
}
