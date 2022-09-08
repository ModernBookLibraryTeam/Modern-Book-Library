package gu_android_team.modernbooklibrary.di

import gu_android_team.modernbooklibrary.ui.mainscreen.MainScreenViewModel
import gu_android_team.modernbooklibrary.ui.searchscreen.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val MAIN_SCREEN_VIEW_MODEL_NAME = "MainScreenViewModel"
const val SEARCH_SCREEN_VIEW_MODEL = "SearchScreenViewModel"

val viewModelsModule = module {
    viewModel {
        MainScreenViewModel(
            usecase = get(named(MAIN_SCREEN_USECASE_NAME))
        )
    }
    viewModel(qualifier = named(SEARCH_SCREEN_VIEW_MODEL)) {
        SearchViewModel(
            usecase = get(named(SEARCH_SCREEN_USECASE_NAME))
        )
    }
}