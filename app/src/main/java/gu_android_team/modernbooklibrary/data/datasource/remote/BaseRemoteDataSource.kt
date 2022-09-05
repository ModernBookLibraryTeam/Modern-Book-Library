package gu_android_team.modernbooklibrary.data.datasource.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

abstract class BaseRemoteDataSource {
    suspend fun <T> apiCall(apiToBeCalled: suspend () -> Response<T>): DataSate<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiToBeCalled()
                if (response.isSuccessful) {
                    DataSate.Success(data = response.body()!!)
                } else {
                    DataSate.Error("Something went wrong!")
                }
            } catch (e: HttpException) {
                DataSate.Error(errorMessage = e.message.toString())
            } catch (e: IOException) {
                DataSate.Error("Please check your network connection")
            } catch (e: Exception) {
                DataSate.Error(errorMessage = e.message.toString())
            }
        }
    }
}