package com.example.twittude

import android.app.Application
import android.util.Log
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.koin.core.logger.MESSAGE
import timber.log.Timber

class TwittudeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        startKoin {
            logger(KoinLogger())
            androidContext(this@TwittudeApplication)
            modules(listOf(
                appDi
            ))
        }
    }


    class KoinLogger() : Logger() {
        override fun log(level: Level, msg: MESSAGE) {
            when (level) {
                Level.DEBUG -> Log.d("KOIN_DEBUG", msg)
                else -> Log.d("KOIN", msg)
            }
        }
    }
}