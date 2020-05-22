package me.mickgian.multimod

import android.app.Application
import me.mickgian.multimod.di.appComponent
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

import org.koin.core.context.startKoin

open class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@App)
            modules(appComponent)
        }
    }
}