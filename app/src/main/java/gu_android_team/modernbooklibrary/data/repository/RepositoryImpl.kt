package gu_android_team.modernbooklibrary.data.repository

import android.util.Log
import gu_android_team.modernbooklibrary.data.datasource.local.LocalDataSourceImpl
import gu_android_team.modernbooklibrary.data.datasource.remote.DataSate
import gu_android_team.modernbooklibrary.data.datasource.remote.NewAndSearchBooksDTO
import gu_android_team.modernbooklibrary.data.datasource.remote.RemoteDataSourceImpl
import gu_android_team.modernbooklibrary.data.datasource.remote.SpecificBookDTO
import gu_android_team.modernbooklibrary.domain.Book
import gu_android_team.modernbooklibrary.domain.LocalDataSource
import gu_android_team.modernbooklibrary.domain.RemoteDataSource
import gu_android_team.modernbooklibrary.domain.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource<Book>
) : Repository {
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun getDataFromLocalDataSource(callback: (List<Book>) -> Unit) {
        scope.launch {
            withContext(Dispatchers.Main) {
                callback(localDataSource.getData())
            }
        }
    }

    override fun insertBookToDB(book: Book) {
        scope.launch {
            withContext(Dispatchers.Main) {
                localDataSource.insert(book)
            }
        }
    }

    override fun deleteBookFromDB(book: Book) {
        scope.launch {
            withContext(Dispatchers.Main) {
                localDataSource.delete(book)
            }
        }
    }

    override val newBooksFromRemoteDataSource: Flow<DataSate<NewAndSearchBooksDTO>>
        get() = flow {
            emit(remoteDataSource.getNewBooksFromServer())
        }

    override val searchedBooksFromRemoteDataSource: Flow<DataSate<NewAndSearchBooksDTO>>
        get() = flow {
            emit(remoteDataSource.getBooksBySearchingFromServer())
        }

    override val bookInfoFromRemoteDataSource: Flow<DataSate<SpecificBookDTO>>
        get() = flow {
            emit(remoteDataSource.getBookInfoFromServer())
        }
}