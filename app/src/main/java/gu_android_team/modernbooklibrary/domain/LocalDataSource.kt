package gu_android_team.modernbooklibrary.domain

interface LocalDataSource<T> {
    suspend fun getData(): List<T>
    suspend fun getDataByTitle(title: String): T
    suspend fun insert(t: T)
    suspend fun delete(t: T)


}