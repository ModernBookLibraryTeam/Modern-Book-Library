package gu_android_team.modernbooklibrary.data.datasource.local

import androidx.room.*

@Dao
interface BookDao {
    @Query("SELECT * FROM LocalBook")
    suspend fun getAllBooks(): List<LocalBook>

    @Query("SELECT * FROM LocalBook WHERE title IN (:title)")
    suspend fun getBooksByTitle(title: String): LocalBook

    @Query("SELECT * FROM LocalBook ORDER BY created_date")
    suspend fun getSortedBooksByDateAsc(): List<LocalBook>

    @Query("SELECT * FROM LocalBook ORDER BY created_date DESC")
    suspend fun getSortedBooksByDateDesc(): List<LocalBook>

    @Query("SELECT * FROM LocalBook ORDER BY author")
    suspend fun getSortedBooksByAuthorAsc(): List<LocalBook>

    @Query("SELECT * FROM LocalBook ORDER BY author DESC")
    suspend fun getSortedBooksByAuthorDesc(): List<LocalBook>

    @Query("SELECT * FROM LocalBook ORDER BY title")
    suspend fun getSortedBooksByTitleAsc(): List<LocalBook>

    @Query("SELECT * FROM LocalBook ORDER BY title DESC")
    suspend fun getSortedBooksByTitleDesc(): List<LocalBook>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: LocalBook)

    @Delete
    suspend fun deleteBook(book: LocalBook)
}