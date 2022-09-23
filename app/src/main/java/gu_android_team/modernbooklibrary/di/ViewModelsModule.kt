package gu_android_team.modernbooklibrary.di

import gu_android_team.modernbooklibrary.ui.bookdescriptionscreen.BookDescriptionScreenViewModel
import gu_android_team.modernbooklibrary.ui.favoritesscreen.FavoritesScreenViewModel
import gu_android_team.modernbooklibrary.ui.mainscreen.MainScreenViewModel
import gu_android_team.modernbooklibrary.ui.profilescreen.ProfileScreenViewModel
import gu_android_team.modernbooklibrary.ui.searchscreen.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val SEARCH_SCREEN_VIEW_MODEL = "SearchScreenViewModel"
const val PROFILE_SCREEN_VIEW_MODEL = "ProfileScreenViewModel"
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

    viewModel(qualifier = named(SEARCH_SCREEN_VIEW_MODEL)) {
        SearchViewModel(
            usecase = get(named(SEARCH_SCREEN_USECASE_NAME))
        )
    }

    viewModel(qualifier = named(PROFILE_SCREEN_VIEW_MODEL)) {
        ProfileScreenViewModel()
    }
}