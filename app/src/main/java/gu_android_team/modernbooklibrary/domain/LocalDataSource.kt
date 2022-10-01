package gu_android_team.modernbooklibrary.domain

import kotlinx.coroutines.flow.Flow

interface LocalDataSource<T> {
    suspend fun getData(): Flow<List<T>>
    suspend fun getDataByTitle(title: String): T
    suspend fun insert(t: T)
    suspend fun delete(t: T)
    suspend fun isExistData(id: String): Boolean

}