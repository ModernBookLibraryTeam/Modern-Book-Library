package gu_android_team.modernbooklibrary.domain

interface Contracts {
    interface MainScreenRepo<T> {
        fun addBookToFavoriteDB(book: T)
        fun deleteBookFromFavoriteDB(book: T)
    }

    interface DescriptionScreenRepo<T> {
        fun addBookToFavoriteDB(book: T)
        fun deleteBookFromFavoriteDB(book: T)
    }

    interface FavoriteScreenRepo<T> {
        fun deleteBookFromFavoriteDB(book: T)
    }

    interface MainScreenViewModel {
        fun getTrendingBooks()
        fun getBooksWeLove()
        fun getBooksReadRecently()
        fun getSeasonalBooks()
    }

    interface DescriptionScreenViewModel<T> {
        fun getInfoAboutBooks()
        fun addBook(book: T)
        fun deleteBook(book: T)
    }

    interface SearchScreenViewModel {
        fun showSearchResult()
    }

    interface FavoriteScreenViewModel<T> {
        fun showFavoriteBooks()
        fun addBook(book: T)
        fun deleteBook(book: T)
    }
}