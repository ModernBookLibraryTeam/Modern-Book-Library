package gu_android_team.modernbooklibrary.data.datasource.remote

import gu_android_team.modernbooklibrary.domain.Book
import gu_android_team.modernbooklibrary.domain.RemoteDataSource
import gu_android_team.modernbooklibrary.domain.mapper.Mapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteDataSourceImpl(
    private val api: RetrofitInt,
    private val mapper: Mapper,
    var request: String,
    var bookId: String,
    var page: String,
) : RemoteDataSource, BaseRemoteDataSource() {

    private suspend fun getNewBooksFromServer() = apiCall { api.getNewBooks() }

    private suspend fun getBooksBySearchingFromServer() =
        apiCall { api.getBooksBySearching(request, page) }

    private suspend fun getBookInfoFromServer() = apiCall { api.getBookInfo(bookId) }

    override suspend fun getMappedNewBooksFromServer() =
        if (getNewBooksFromServer() is DataSate.Success) {
            DataSate.Success(
                mapper.mapRemoteDataToLocal(
                    getNewBooksFromServer().data?.books ?: emptyList()
                )
            )
        } else {
            DataSate.Error(getNewBooksFromServer().message.toString())
        }

    override suspend fun getMappedBooksBySearchingFromServer() =
        if (getBooksBySearchingFromServer() is DataSate.Success) {
            DataSate.Success(
                mapper.mapRemoteDataToLocal(
                    getBooksBySearchingFromServer().data?.books ?: emptyList()
                )
            )
        } else {
            DataSate.Error(getBooksBySearchingFromServer().message.toString())
        }

    override suspend fun getMappedBookInfoFromServer() =
        if (getBookInfoFromServer() is DataSate.Success) {
            DataSate.Success(
                mapper.mapRemoteDataSpecificToLocal(getBookInfoFromServer().data)
            )
        } else {
            DataSate.Error(getBookInfoFromServer().message.toString())
        }
}
