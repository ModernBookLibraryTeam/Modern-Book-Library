package gu_android_team.modernbooklibrary.data.datasource.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Query("SELECT * FROM LocalBook")
    fun getAllBooks(): Flow<List<LocalBook>>

    @Query("SELECT * FROM LocalBook WHERE title IN (:title)")
    suspend fun getBooksByTitle(title: String): LocalBook

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: LocalBook)

    @Delete
    suspend fun deleteBook(book: LocalBook)

    @Query("DELETE from LocalBook WHERE isbn13 IN (:idBook)")
    fun  deleteById(idBook: String)

    @Query("SELECT EXISTS(SELECT * FROM LocalBook WHERE isbn13 IN (:idBook))")
    fun isBookExist(idBook: String): Boolean
}