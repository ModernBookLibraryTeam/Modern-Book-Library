package gu_android_team.modernbooklibrary.utils

sealed class AppState{
    data class AppStateSuccess<T>(val value: T) : AppState()
    data class AppStateError(val error: Throwable) : AppState()
    object AppStateLoading : AppState()
}