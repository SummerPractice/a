package com.example.polycareer.di.screen

import com.example.polycareer.domain.usecase.MainScreenUseCase
import com.example.polycareer.screens.main.main_screen.MainScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    single { MainScreenUseCase(get()) }
    viewModel { MainScreenViewModel(get()) }
}