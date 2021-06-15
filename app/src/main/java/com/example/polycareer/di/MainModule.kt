package com.example.polycareer.di

import com.example.polycareer.data.UserRepositoryTest
import com.example.polycareer.domain.repository.UserRepository
import com.example.polycareer.domain.usecase.AuthUseCase
import com.example.polycareer.domain.usecase.GradesUseCase
import com.example.polycareer.screens.auth.sign_up.SingUpViewModel
import com.example.polycareer.screens.main.grades.GradesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    single<UserRepository> { UserRepositoryTest() }
    single { AuthUseCase(get()) }
    single { GradesUseCase(get()) }
    viewModel { SingUpViewModel(get()) }
    viewModel { GradesViewModel(get()) }
}