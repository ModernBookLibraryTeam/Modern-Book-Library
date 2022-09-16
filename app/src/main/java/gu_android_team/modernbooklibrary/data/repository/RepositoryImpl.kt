package gu_android_team.modernbooklibrary.data.repository

import gu_android_team.modernbooklibrary.data.datasource.remote.DataState
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

class RepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource<Book>
) : Repository {
    private val scope = CoroutineScope(Dispatchers.IO)

    override suspend fun getNewBooksFromRemoteDataSource(): Flow<DataState<List<Book>>> = flow {
            emit(remoteDataSource.getMappedNewBooksFromServer())
        }

    override suspend fun getSearchedBooksFromRemoteDataSource(
        searchWord: String,
        page: String
    ): Flow<DataState<List<Book>>> = flow {
        emit(remoteDataSource.getMappedBooksBySearchingFromServer(searchWord, page))
    }

    override suspend fun getBookInfoFromRemoteDataSource(bookIsbn13: String): Flow<DataState<Book>> = flow {
        emit(remoteDataSource.getMappedBookInfoFromServer(bookIsbn13))
    }

    override suspend fun getDataFromLocalDataSource(): Flow<List<Book>> {
        return localDataSource.getData()

    }

    override suspend fun isExistsDataFromLocalDataSource(id: String): Boolean {
        return localDataSource.isExistData(id)
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
}