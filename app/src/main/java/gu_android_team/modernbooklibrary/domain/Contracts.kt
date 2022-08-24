package gu_android_team.modernbooklibrary.domain

interface Contracts {

    fun <T> addBookToFavorite(book: T)
    fun <T> deleteBookFromFavorite(book: T)

    interface ProjectRepo {
        fun <T> addBookToFavoriteDB(book: T)
        fun <T> deleteBookFromFavoriteDB(book: T)
    }

    interface MainScreenViewModel {
        fun getTrendingBooks()
        fun getBooksWeLove()
        fun getBooksReadRecently()
        fun getSeasonalBooks()
    }

    interface DescriptionScreenViewModel {
        fun getInfoAboutBooks()
    }

    interface SearchScreenViewModel {
        fun showSearchResult()
    }

    interface FavoriteScreenViewModel {
        fun showFavoriteBooks()
    }
}