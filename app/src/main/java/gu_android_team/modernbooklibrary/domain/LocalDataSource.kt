package gu_android_team.modernbooklibrary.domain

interface LocalDataSource<T> {
    suspend fun getData(): List<T>
    suspend fun getDataByTitle(title: String): T
    suspend fun getDataSortedByDate(): List<T>
    suspend fun getDataSortedByDateDesc(): List<T>
    suspend fun getDataSortedByAuthor(): List<T>
    suspend fun getDataSortedByAuthorDesc(): List<T>
    suspend fun getDataSortedByName(): List<T>
    suspend fun getDataSortedByNameDesc(): List<T>
    suspend fun insert(t: T)
    suspend fun delete(t: T)


}