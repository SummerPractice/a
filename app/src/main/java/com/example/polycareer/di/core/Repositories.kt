package com.example.polycareer.di.core

import com.example.polycareer.data.repository.*
import com.example.polycareer.domain.repository.ProfessionRepository
import com.example.polycareer.domain.repository.ResultsRepository
import com.example.polycareer.domain.repository.UserCache
import com.example.polycareer.domain.repository.UserRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.binds
import org.koin.dsl.module

val repositoryModule = module {
    single { UserRepositoryImpl(get(), androidContext()) } binds arrayOf(
        UserRepository::class,
        UserCache::class
    )

    single<ResultsRepository> { ResultRepositoryImpl(get()) }

    single<ProfessionRepository> { ProfessionsRepositoryImpl() }

    single { QuizItemsLocalRepository(get()) }

    single { QuizItemsRemoteRepository(get()) }
}