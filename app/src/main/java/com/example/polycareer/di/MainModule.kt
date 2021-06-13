package com.example.polycareer.di

import com.example.polycareer.data.AuthRepositoryTest
import com.example.polycareer.domain.repository.AuthRepository
import com.example.polycareer.domain.usecase.AuthUseCase
import com.example.polycareer.screens.auth.sign_up.SingUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    single<AuthRepository> { AuthRepositoryTest() }
    single { AuthUseCase(get()) }
    viewModel { SingUpViewModel(get()) }
}