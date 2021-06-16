package com.example.polycareer.di

import android.content.Context
import androidx.room.Room
import com.example.polycareer.data.database.UserDao
import com.example.polycareer.data.database.UserDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module(createdAtStart = true) {
    fun provideDatabase(context: Context): UserDatabase {
        return Room.databaseBuilder(context, UserDatabase::class.java, "user_database")
            .build()
    }

    fun provideUserDao(database: UserDatabase): UserDao {
        return database.userDao
    }

    single { provideDatabase(androidApplication()) }
    single { provideUserDao(get()) }
}