package gu_android_team.modernbooklibrary.di

import android.app.Application
import androidx.room.Room
import gu_android_team.modernbooklibrary.data.datasource.LocalDataSourceImpl
import gu_android_team.modernbooklibrary.data.datasource.local.BookDao
import gu_android_team.modernbooklibrary.data.datasource.local.BookDatabase
import gu_android_team.modernbooklibrary.data.datasource.local.LocalMapperImpl
import gu_android_team.modernbooklibrary.data.datasource.remote.RemoteDataSourceImpl
import gu_android_team.modernbooklibrary.data.datasource.remote.RetrofitInt
import gu_android_team.modernbooklibrary.data.repository.RepositoryImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

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