package gu_android_team.modernbooklibrary.data.datasource.remote

import gu_android_team.modernbooklibrary.domain.Book
import gu_android_team.modernbooklibrary.domain.RemoteDataSource
import gu_android_team.modernbooklibrary.domain.mapper.Mapper

class RemoteDataSourceImpl(
    private val api: RetrofitInt,
    private val mapper: Mapper,
    var request: String,
    var bookId: String,
    var page: String,
) : RemoteDataSource, BaseRemoteDataSource() {

    suspend fun getNewBooksFromServer() = apiCall { api.getNewBooks() }

    suspend fun getBooksBySearchingFromServer() = apiCall { api.getBooksBySearching(request, page) }

    suspend fun getBookInfoFromServer() = apiCall { api.getBookInfo(bookId) }


    /*
    override val newBooks: Flow<AppState>
            get() = flow {
                emit(getResponse(api.getNewBooks()))
            }
    override val searchedBooks: Flow<Call<NewAndSearchBooksDTO>>
        get() = flow {
            emit(api.getBooksBySearching(request, page))
        }

    override val bookInfo: Flow<Call<SpecificBookDTO>>
        get() = flow {
            emit(api.getBookInfo(bookId))
        }
*/
}