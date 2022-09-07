package gu_android_team.modernbooklibrary.data.datasource.remote

import gu_android_team.modernbooklibrary.domain.Book
import gu_android_team.modernbooklibrary.domain.RemoteDataSource
import gu_android_team.modernbooklibrary.domain.mapper.Mapper

class RemoteDataSourceImpl(
    private val api: RetrofitInt,
    private val mapper: Mapper
) : RemoteDataSource, BaseRemoteDataSource() {

    private suspend fun getNewBooksFromServer() = apiCall { api.getNewBooks() }

    private suspend fun getBooksBySearchingFromServer(searchWord: String, page: String) =
        apiCall { api.getBooksBySearching(searchWord, page) }

    private suspend fun getBookInfoFromServer(bookIsbn13: String) =
        apiCall { api.getBookInfo(bookIsbn13) }

    override suspend fun getMappedNewBooksFromServer(): DataState<List<Book>> {
        val data = getNewBooksFromServer()

        return if (data is DataState.Success) {
            DataState.Success(
                mapper.mapRemoteDataToAppData(
                    data.data?.books ?: emptyList()
                )
            )
        } else {
            DataState.Error(data.message.toString())
        }
    }

    override suspend fun getMappedBooksBySearchingFromServer(
        searchWord: String,
        page: String
    ): DataState<List<Book>> {
        val data = getBooksBySearchingFromServer(searchWord, page)

        return if (data is DataState.Success) {
            DataState.Success(
                mapper.mapRemoteDataToAppData(
                    data.data?.books ?: emptyList()
                )
            )
        } else {
            DataState.Error(data.message.toString())
        }
    }

    override suspend fun getMappedBookInfoFromServer(bookIsbn13: String): DataState<Book> {
        val data = getBookInfoFromServer(bookIsbn13)

        return if (data is DataState.Success) {
            DataState.Success(
                mapper.mapRemoteSpecificBookDtoToBook(data.data)
            )
        } else {
            DataState.Error(data.message.toString())
        }
    }
}
