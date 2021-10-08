package com.example.polycareer.screens.main.main_screen

import com.example.polycareer.base.BaseAction
import com.example.polycareer.base.BaseState
import com.example.polycareer.base.BaseViewModel
import com.example.polycareer.domain.usecase.MainScreenUseCase

class MainScreenViewModel(
    private val useCase: MainScreenUseCase
) : BaseViewModel<MainScreenViewModel.MainScreenState, MainScreenViewModel.MainScreenAction>(
    MainScreenState()
) {
    fun onCreateView() {
        useCase.checkRegister().also { result ->
            when (result) {
                is MainScreenUseCase.RegisterResult.Registered ->
                    sendAction(MainScreenAction.UserRegistered)

                is MainScreenUseCase.RegisterResult.NotRegistered ->
                    sendAction(MainScreenAction.UserNotRegistered)
            }
        }
    }

    override fun onReduceState(action: MainScreenAction): MainScreenState = when (action) {
        is MainScreenAction.UserRegistered -> state.copy(isRegistered = true)
        is MainScreenAction.UserNotRegistered -> state.copy(isRegistered = false)
    }

    sealed interface MainScreenAction : BaseAction {
        object UserRegistered : MainScreenAction
        object UserNotRegistered : MainScreenAction
    }

    data class MainScreenState(
        val isRegistered: Boolean = false
    ) : BaseState
}
