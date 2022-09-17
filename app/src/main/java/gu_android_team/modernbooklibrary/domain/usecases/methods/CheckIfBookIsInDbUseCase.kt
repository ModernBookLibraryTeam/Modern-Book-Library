package gu_android_team.modernbooklibrary.domain.usecases.methods

import kotlinx.coroutines.flow.Flow

interface CheckIfBookIsInDbUseCase {
    suspend fun checkIfBookIsInDb(isbn13: String): Flow<Boolean>
}