package gu_android_team.modernbooklibrary.data.datasource.local

import androidx.room.*

@Dao
interface BookDao {
    @Query("SELECT * FROM LocalBook")
    suspend fun getAllBooks(): List<LocalBook>

    @Query("SELECT * FROM LocalBook WHERE title IN (:title)")
    suspend fun getBooksByTitle(title: String): LocalBook

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: LocalBook)

    @Delete
    suspend fun deleteBook(book: LocalBook)
}