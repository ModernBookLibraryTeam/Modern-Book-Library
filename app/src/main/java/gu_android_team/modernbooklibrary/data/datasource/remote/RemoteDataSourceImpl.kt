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

    override val newBooks: Flow<List<Book>>
        get() = flow {
            emit(mapper.mapRemoteDataToLocal(getNewBooksFromServer().data?.books ?: emptyList()))
        }

    override val searchedBooks: Flow<List<Book>>
        get() = flow {
            emit(
                mapper.mapRemoteDataToLocal(
                    getBooksBySearchingFromServer().data?.books ?: emptyList()
                )
            )
        }

    override val bookInfo: Flow<Book>
        get() = flow {
            emit(mapper.mapRemoteDataSpecificToLocal(getBookInfoFromServer().data))
        }

}
