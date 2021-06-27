package com.example.polycareer.di.core

import com.example.polycareer.data.repository.*
import com.example.polycareer.domain.repository.*
import com.example.polycareer.domain.repository.ResultsRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.binds
import org.koin.dsl.module

val repositoryModule = module {
    single { UserLocalRepository(get(), androidContext()) } binds arrayOf(
        UserRepository::class,
        UserCache::class
    )

    single { UserRemoteRepository(get()) }

    single<ResultsInfoRepository> { ResultsInfoRepositoryImpl(get()) }

    single<ResultsRepository> { ResultRepositoryImpl(get()) }

    single { QuizItemsLocalRepository(get()) }

    single { QuizItemsRemoteRepository(get()) }

    single { DirectionsLocalRepository(get()) }

    single { DirectionsRemoteRepository(get()) }

    single { ProfessionsLocalRepository(get()) }

    single { ProfessionsRemoteRepository(get()) }
}