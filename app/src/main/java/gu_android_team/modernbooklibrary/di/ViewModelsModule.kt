package gu_android_team.modernbooklibrary.di

import gu_android_team.modernbooklibrary.ui.bookdescriptionscreen.BookDescriptionScreenViewModel
import gu_android_team.modernbooklibrary.ui.favoritesscreen.FavoritesScreenViewModel
import gu_android_team.modernbooklibrary.ui.mainscreen.MainScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel {
        MainScreenViewModel(
            usecase = get(named(MAIN_SCREEN_USECASE_NAME)),
            app = get()
        )
    }

    viewModel {
        BookDescriptionScreenViewModel(
            usecase = get(named(BOOK_DESCRIPTION_SCREEN_USECASE_NAME))
        )
    }

    viewModel {
        FavoritesScreenViewModel(
            usecase = get(named(FAVORITES_SCREEN_USECASE_NAME))
        )
    }
}