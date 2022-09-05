package gu_android_team.modernbooklibrary.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [LocalBook::class], version = 1)
@TypeConverters(Converters::class)
abstract class BookDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
}