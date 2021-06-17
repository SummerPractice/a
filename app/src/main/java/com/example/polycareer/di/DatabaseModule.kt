package com.example.polycareer.di

import android.content.Context
import androidx.room.Room
import com.example.polycareer.data.database.QuizDao
import com.example.polycareer.data.database.UserDao
import com.example.polycareer.data.database.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module(createdAtStart = true) {
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "user_database")
            .build()
    }

    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao
    }

    fun provideQuizDao(database: AppDatabase): QuizDao {
        return database.quizDao
    }

    single { provideDatabase(androidApplication()) }
    single { provideUserDao(get()) }
    single { provideQuizDao(get()) }
}