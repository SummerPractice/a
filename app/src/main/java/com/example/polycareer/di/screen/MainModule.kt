package com.example.polycareer.di.screen

import com.example.polycareer.domain.usecase.MainScreenUseCase
import com.example.polycareer.domain.usecase.OldResultsUseCase
import com.example.polycareer.screens.main.main_screen.MainScreenViewModel
import com.example.polycareer.screens.main.old_results.OldResultsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    single { MainScreenUseCase(get(), get()) }
    viewModel { MainScreenViewModel(get()) }

    single { OldResultsUseCase(get()) }
    viewModel { OldResultsViewModel(get()) }
}