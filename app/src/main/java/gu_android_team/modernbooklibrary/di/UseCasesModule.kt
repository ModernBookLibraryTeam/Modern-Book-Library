package gu_android_team.modernbooklibrary.di

import gu_android_team.modernbooklibrary.data.usacases.BookDescriptionScreenUseCaseImpl
import gu_android_team.modernbooklibrary.data.usacases.FavoritesScreenUseCaseImpl
import gu_android_team.modernbooklibrary.data.usacases.MainScreenUseCaseImpl
import gu_android_team.modernbooklibrary.domain.usecases.screens.BookDescriptionScreenUseCase
import gu_android_team.modernbooklibrary.domain.usecases.screens.FavoritesScreenUseCase
import gu_android_team.modernbooklibrary.domain.usecases.screens.MainScreenUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val MAIN_SCREEN_USECASE_NAME = "MainScreenUsaCase"
const val BOOK_DESCRIPTION_SCREEN_USECASE_NAME = "BookDescriptionScreenUseCase"
const val FAVORITES_SCREEN_USECASE_NAME = "FavoritesScreenUseCase"

val useCasesModule = module {
    factory<MainScreenUseCase>(qualifier = named(MAIN_SCREEN_USECASE_NAME)) {
        MainScreenUseCaseImpl(get(named(REPOSITORY_IMPL)))
    }

    factory<BookDescriptionScreenUseCase>(qualifier = named(BOOK_DESCRIPTION_SCREEN_USECASE_NAME)) {
        BookDescriptionScreenUseCaseImpl(get(named(REPOSITORY_IMPL)))
    }

    factory<FavoritesScreenUseCase>(qualifier = named(FAVORITES_SCREEN_USECASE_NAME)) {
        FavoritesScreenUseCaseImpl(get(named(REPOSITORY_IMPL)))
    }
}