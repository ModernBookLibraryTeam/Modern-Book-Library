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
        fun getAllBooksFromDB(): T
        fun deleteBookFromFavoriteDB(book: T)
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
}