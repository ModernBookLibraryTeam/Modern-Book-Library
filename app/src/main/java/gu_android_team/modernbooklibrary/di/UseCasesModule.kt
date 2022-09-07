package gu_android_team.modernbooklibrary.di

import gu_android_team.modernbooklibrary.data.usacases.MainScreenUseCaseImpl
import gu_android_team.modernbooklibrary.domain.usecases.screens.MainScreenUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val MAIN_SCREEN_USECASE_NAME = "MainScreenUsaCase"

val useCasesModule = module {
    factory<MainScreenUseCase>(qualifier = named(MAIN_SCREEN_USECASE_NAME)) {
        MainScreenUseCaseImpl(get(named(REPOSITORY_IMPL)))
    }
}