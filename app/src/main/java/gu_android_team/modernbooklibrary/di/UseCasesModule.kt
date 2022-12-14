package gu_android_team.modernbooklibrary.di

import gu_android_team.modernbooklibrary.data.usacases.*
import gu_android_team.modernbooklibrary.domain.usecases.screens.*
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val MAIN_SCREEN_USECASE_NAME = "MainScreenUsaCase"
const val BOOK_DESCRIPTION_SCREEN_USECASE_NAME = "BookDescriptionScreenUseCase"
const val FAVORITES_SCREEN_USECASE_NAME = "FavoritesScreenUseCase"
const val SEARCH_SCREEN_USECASE_NAME = "SearchScreenUseCase"
const val PROFILE_SCREEN_USECASE_NAME = "ProfileScreenUseCase"

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

    factory<SearchScreenUsecase>(qualifier = named(SEARCH_SCREEN_USECASE_NAME)) {
        SearchScreenUseCaseImpl(get(named(REPOSITORY_IMPL)))
    }

    factory<ProfileScreenUseCase>(qualifier = named(PROFILE_SCREEN_USECASE_NAME)) {
        ProfileScreenUseCaseImpl(get(named(REPOSITORY_IMPL)))
    }
}