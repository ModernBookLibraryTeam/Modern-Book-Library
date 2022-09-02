package gu_android_team.modernbooklibrary.data.repository

import android.util.Log
import gu_android_team.modernbooklibrary.data.datasource.remote.NewAndSearchBooksDTO
import gu_android_team.modernbooklibrary.data.datasource.remote.RemoteDataSourceImpl
import gu_android_team.modernbooklibrary.data.datasource.remote.SpecificBookDTO
import gu_android_team.modernbooklibrary.domain.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryImpl(private val remoteDataSourceImpl: RemoteDataSourceImpl) : Repository {
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun getNewBooksFromRemoteDataSource(callback: (Response<NewAndSearchBooksDTO>) -> Unit) {
        scope.launch {
            remoteDataSourceImpl.newBooks.collect {
                it.enqueue(object : Callback<NewAndSearchBooksDTO> {
                    override fun onResponse(
                        call: Call<NewAndSearchBooksDTO>,
                        response: Response<NewAndSearchBooksDTO>
                    ) {
                        if (response.isSuccessful) {
                            callback(response)
                        }
                    }

                    override fun onFailure(call: Call<NewAndSearchBooksDTO>, t: Throwable) {
                        Log.d("TAG", t.message.toString())
                    }

                })
            }
        }
    }

    override fun getSearchedBooksFromRemoteDataSource(callback: (Response<NewAndSearchBooksDTO>) -> Unit) {
        scope.launch {
            remoteDataSourceImpl.searchedBooks.collect {
                it.enqueue(object : Callback<NewAndSearchBooksDTO> {
                    override fun onResponse(
                        call: Call<NewAndSearchBooksDTO>,
                        response: Response<NewAndSearchBooksDTO>
                    ) {
                        if (response.isSuccessful) {
                            callback(response)
                        }
                    }

                    override fun onFailure(call: Call<NewAndSearchBooksDTO>, t: Throwable) {
                        Log.d("TAG", t.message.toString())
                    }

                })
            }
        }
    }

    override fun getBookInfoFromRemoteDataSource(callback: (Response<SpecificBookDTO>) -> Unit) {
        scope.launch {
            remoteDataSourceImpl.bookInfo.collect {
                it.enqueue(object : Callback<SpecificBookDTO> {
                    override fun onResponse(
                        call: Call<SpecificBookDTO>,
                        response: Response<SpecificBookDTO>
                    ) {
                        if (response.isSuccessful) {
                            callback(response)
                        }
                    }

                    override fun onFailure(call: Call<SpecificBookDTO>, t: Throwable) {
                        Log.d("TAG", t.message.toString())
                    }

                })
            }
        }
    }
}