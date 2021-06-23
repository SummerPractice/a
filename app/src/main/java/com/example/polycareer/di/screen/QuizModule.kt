package com.example.polycareer.di.screen

import com.example.polycareer.domain.usecase.QuizItemUseCase
import com.example.polycareer.domain.usecase.QuizResultUseCase
import com.example.polycareer.screens.quiz.quiz_item.QuizItemViewModel
import com.example.polycareer.screens.quiz.quiz_results.QuizResultsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val quizModule = module {
    single { QuizItemUseCase(get(), get(), get()) }
    viewModel { QuizItemViewModel(get()) }

    single { QuizResultUseCase(get(), get(), get(), get()) }
    viewModel { QuizResultsViewModel(get()) }
}