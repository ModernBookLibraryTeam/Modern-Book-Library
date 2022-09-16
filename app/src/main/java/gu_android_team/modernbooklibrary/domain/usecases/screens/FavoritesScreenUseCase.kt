package gu_android_team.modernbooklibrary.domain.usecases.screens

import gu_android_team.modernbooklibrary.domain.usecases.methods.DeleteBookById
import gu_android_team.modernbooklibrary.domain.usecases.methods.GetBooksInCache

interface FavoritesScreenUseCase: GetBooksInCache, DeleteBookById {
}