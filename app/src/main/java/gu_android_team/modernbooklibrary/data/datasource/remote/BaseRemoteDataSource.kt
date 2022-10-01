package gu_android_team.modernbooklibrary.data.datasource.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

const val SIMPLE_ERROR_MESSAGE = "Something went wrong!"
const val INTERNET_ERROR_MESSAGE = "Please check your network connection"
abstract class BaseRemoteDataSource {
    suspend fun <T> apiCall(apiToBeCalled: suspend () -> Response<T>): DataState<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiToBeCalled()
                if (response.isSuccessful) {
                    DataState.Success(data = response.body()!!)
                } else {
                    DataState.Error(SIMPLE_ERROR_MESSAGE)
                }
            } catch (e: HttpException) {
                DataState.Error(errorMessage = e.message.toString())
            } catch (e: IOException) {
                DataState.Error(INTERNET_ERROR_MESSAGE)
            } catch (e: Exception) {
                DataState.Error(errorMessage = e.message.toString())
            }
        }
    }
}