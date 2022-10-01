package gu_android_team.modernbooklibrary.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LocalBook::class], version = 2)
abstract class BookDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
}