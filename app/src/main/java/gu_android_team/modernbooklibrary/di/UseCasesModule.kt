package gu_android_team.modernbooklibrary.di

import gu_android_team.modernbooklibrary.data.repository.FakeRepo
import gu_android_team.modernbooklibrary.data.usacases.MainScreenUseCaseImpl
import gu_android_team.modernbooklibrary.domain.usecases.screens.MainScreenUsaCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val MAIN_SCREEN_USECASE_NAME = "MainScreenUsaCase"

val useCasesModule = module {
    factory<MainScreenUsaCase>(qualifier = named(MAIN_SCREEN_USECASE_NAME)) {
        MainScreenUseCaseImpl(FakeRepo())
    }
}