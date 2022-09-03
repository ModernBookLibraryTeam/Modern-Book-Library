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

const val LOCAL_DATA_SOURCE_IMPL = "LocalDataSourceImpl"
const val OKHTTP_CLIENT = "OkHttpClient"
const val RETROFIT_INIT = "RetrofitInit"
const val RETROFIT_INT = "RetrofitInt"
const val REMOTE_DATA_SOURCE_IMPL = "RemoteDataSource"
const val REPOSITORY_IMPL = "RepositoryImpl"
const val BOOK_DAO = "BookDao"
const val LOCAL_MAPPER = "LocalMapper"
const val BOOK_DATABASE = "BookDatabase"

val remoteModule = module {

    fun provideDatabase(application: Application): BookDatabase {
        return Room.databaseBuilder(application, BookDatabase::class.java, "books")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideBookDao(database: BookDatabase): BookDao {
        return database.bookDao()
    }

    single(qualifier = named(BOOK_DATABASE)) { provideDatabase(androidApplication()) }

    single(qualifier = named(BOOK_DAO)) { provideBookDao(get(named(BOOK_DATABASE))) }

    single(qualifier = named(LOCAL_MAPPER)) { LocalMapperImpl() }

    single(qualifier = named(LOCAL_DATA_SOURCE_IMPL)) {
        LocalDataSourceImpl(
            get(named(BOOK_DAO)),
            get(named(LOCAL_MAPPER))
        )
    }

    single(qualifier = named(OKHTTP_CLIENT)) {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    single(qualifier = named(RETROFIT_INIT)) {
        Retrofit.Builder()
            .baseUrl("https://api.itbook.store/")
            .client(get(named(OKHTTP_CLIENT)))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<RetrofitInt>(qualifier = named(RETROFIT_INT)) {
        get<Retrofit>(named(RETROFIT_INIT)).create(
            RetrofitInt::class.java
        )
    }

    single(qualifier = named(REMOTE_DATA_SOURCE_IMPL)) {
        RemoteDataSourceImpl(get(named(REMOTE_DATA_SOURCE_IMPL)), "", "", "")
    }

    single(qualifier = named(REPOSITORY_IMPL)) {
        RepositoryImpl(get(named(REMOTE_DATA_SOURCE_IMPL)), get())
    }
}
