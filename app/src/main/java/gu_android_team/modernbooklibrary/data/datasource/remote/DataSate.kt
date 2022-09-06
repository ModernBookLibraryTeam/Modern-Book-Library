package gu_android_team.modernbooklibrary.data.datasource.remote

sealed class DataSate<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : DataSate<T>(data = data)
    class Error<T>(errorMessage: String) : DataSate<T>(message = errorMessage)
}