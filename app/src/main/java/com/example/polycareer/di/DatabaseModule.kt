package com.example.polycareer.di

import android.content.Context
import androidx.room.Room
import com.example.polycareer.data.database.UserDao
import com.example.polycareer.data.database.UserDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
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

//    single {
//        Room.databaseBuilder(androidContext(), UserDatabase::class.java, "user_database")
//            .fallbackToDestructiveMigration()
//            .build()
//    }
//    single { get<UserDatabase>().userDao }

// fun provideDatabase(application: Application): CountriesDatabase {
//       return Room.databaseBuilder(application, CountriesDatabase::class.java, "countries")
//            .fallbackToDestructiveMigration()
//            .build()
//    }
//
//    fun provideCountriesDao(database: CountriesDatabase): CountriesDao {
//        return  database.countriesDao
//    }
//
//    single { provideDatabase(androidApplication()) }
//    single { provideCountriesDao(get()) }