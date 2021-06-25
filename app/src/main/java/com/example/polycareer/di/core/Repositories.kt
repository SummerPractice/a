package com.example.polycareer.di.core

import com.example.polycareer.data.repository.*
import com.example.polycareer.domain.repository.*
import com.example.polycareer.domain.repository.ResultsRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.binds
import org.koin.dsl.module

val repositoryModule = module {
    single { UserRepositoryImpl(get(), androidContext()) } binds arrayOf(
        UserRepository::class,
        UserCache::class
    )

    single<ResultsInfoRepository> { ResultsInfoRepositoryImpl(get()) }

    single<ResultsRepository> { ResultRepositoryImpl(get()) }

    single<ProfessionRepository> { ProfessionsRepositoryImpl() }

    single { QuizItemsLocalRepository(get()) }

    single { QuizItemsRemoteRepository(get()) }

    single { DirectionsLocalRepository(get()) }

    single { DirectionsRemoteRepository(get()) }
}