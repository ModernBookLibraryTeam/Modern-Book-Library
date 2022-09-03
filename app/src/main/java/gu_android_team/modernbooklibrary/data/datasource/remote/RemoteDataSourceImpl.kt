package gu_android_team.modernbooklibrary.data.datasource.remote

import gu_android_team.modernbooklibrary.domain.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Call

class RemoteDataSourceImpl(
    private val api: RetrofitInt,
    var request: String,
    var bookId: String,
    var page: String,
) :
    RemoteDataSource {

    override val newBooks: Flow<Call<NewAndSearchBooksDTO>>
        get() = flow {
            emit(api.getNewBooks())
        }
    override val searchedBooks: Flow<Call<NewAndSearchBooksDTO>>
        get() = flow {
            emit(api.getBooksBySearching(request))
        }

    override val nextPageOfSearchedBooks: Flow<Call<NewAndSearchBooksDTO>>
        get() = flow {
            emit(api.getNextPageOfSearchedBooks(request, page))
        }

    override val bookInfo: Flow<Call<SpecificBookDTO>>
        get() = flow {
            emit(api.getBookInfo(bookId))
        }

}