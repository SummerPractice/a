package com.example.polycareer.di.core

import com.example.polycareer.data.api.ApiFactory
import org.koin.dsl.module

val networkModule = module {
    single { ApiFactory.retrofit }
}