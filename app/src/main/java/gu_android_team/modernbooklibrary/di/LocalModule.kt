package gu_android_team.modernbooklibrary.di

import android.app.Application
import androidx.room.Room
import gu_android_team.modernbooklibrary.data.datasource.local.LocalDataSourceImpl
import gu_android_team.modernbooklibrary.data.datasource.local.BookDao
import gu_android_team.modernbooklibrary.data.datasource.local.BookDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val localModule = module {
    fun provideDatabase(application: Application): BookDatabase {
        return Room.databaseBuilder(application, BookDatabase::class.java, "books")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideBookDao(database: BookDatabase): BookDao {
        return database.bookDao()
    }

    single { provideDatabase(androidApplication()) }
    single { provideBookDao(get()) }
    single { LocalMapperImpl() }
    single { LocalDataSourceImpl(get(), get()) }
}