package gu_android_team.modernbooklibrary

import android.app.Application
import gu_android_team.modernbooklibrary.di.localModule
import gu_android_team.modernbooklibrary.di.remoteModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@App)
            modules(localModule, remoteModule)
        }
    }
}