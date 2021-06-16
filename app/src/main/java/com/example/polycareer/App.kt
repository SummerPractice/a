package com.example.polycareer

import android.app.Application
import android.content.Context
import com.example.polycareer.di.databaseModule
import com.example.polycareer.di.authModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: App? = null

        const val CURRENT_USER_ID = "CURRENT_USER_ID"
        const val USER_ID_KEY = "user_id"
        const val USER_ID_DEFAULT_VALUE: Long = -1

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                databaseModule,
                authModule
            )
        }
    }
}