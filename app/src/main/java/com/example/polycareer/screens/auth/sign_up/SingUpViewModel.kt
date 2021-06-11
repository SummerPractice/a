package com.example.polycareer.screens.auth.sign_up

import androidx.lifecycle.viewModelScope
import com.example.polycareer.base.BaseAction
import com.example.polycareer.base.BaseState
import com.example.polycareer.base.BaseViewModel
import com.example.polycareer.domain.model.UserDetails
import com.example.polycareer.domain.usecase.AuthUseCase
import kotlinx.coroutines.launch


class SingUpViewModel(
    private val authUseCase: AuthUseCase
) : BaseViewModel<SingUpViewModel.AuthState, SingUpViewModel.AuthAction>(AuthState()) {

    fun saveUserDetail(name: String, surname: String, email: String) {
        viewModelScope.launch {
            authUseCase.execute(UserDetails(name, surname, email)).also { result ->
                when (result) {
                    is AuthUseCase.Result.DataCorrect -> sendAction(AuthAction.UserDetailSaved)
                    is AuthUseCase.Result.WrongData -> sendAction(AuthAction.WrongUserDetail(result.errorMessage))
                }
            }
        }
    }

    override fun onReduceState(viewAction: AuthAction) = when(viewAction) {
        is AuthAction.UserDetailSaved -> state.copy(
            isSaved = true,
            errorMessage = ""
        )
        is AuthAction.WrongUserDetail -> state.copy(
            isSaved = false,
            errorMessage = viewAction.message
        )
    }

    fun navigationComplete() {
        state = state.copy(false, "")
    }

    sealed class AuthAction : BaseAction {
        object UserDetailSaved : AuthAction()
        class WrongUserDetail(val message: String) : AuthAction()
    }

    data class AuthState(
        val isSaved: Boolean = false,
        val errorMessage: String = ""
    ) : BaseState
}