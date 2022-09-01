package gu_android_team.modernbooklibrary.data.datasource.remote

import kotlinx.coroutines.flow.flow

class RemoteDataSource(private val api: RetrofitInt, var request: String, var bookId: String) {

    val newBooks = flow {
        emit(api.getNewBooks())
    }

    val searchedBooks = flow {
        emit(api.getBooksBySearching(request))
    }

    val bookInfo = flow {
        emit(api.getBookInfo(bookId))
    }
}