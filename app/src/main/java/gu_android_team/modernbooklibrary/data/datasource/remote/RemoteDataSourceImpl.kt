package gu_android_team.modernbooklibrary.data.datasource.remote

import gu_android_team.modernbooklibrary.domain.Book
import gu_android_team.modernbooklibrary.domain.RemoteDataSource
import gu_android_team.modernbooklibrary.domain.mapper.Mapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteDataSourceImpl(
    private val api: RetrofitInt,
    var request: String,
    var bookId: String,
    var page: String,
) : RemoteDataSource, BaseRemoteDataSource() {

    override suspend fun getNewBooksFromServer() = apiCall { api.getNewBooks() }

    override suspend fun getBooksBySearchingFromServer() =
        apiCall { api.getBooksBySearching(request, page) }

    override suspend fun getBookInfoFromServer() = apiCall { api.getBookInfo(bookId) }
}
