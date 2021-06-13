package com.example.polycareer.screens.auth.sign_up

import androidx.lifecycle.viewModelScope
import com.example.polycareer.base.BaseAction
import com.example.polycareer.base.BaseState
import com.example.polycareer.base.BaseViewModel
import com.example.polycareer.domain.model.UserDetails
import com.example.polycareer.domain.usecase.AuthUseCase
import kotlinx.coroutines.*


class SingUpViewModel(
    private val authUseCase: AuthUseCase
) : BaseViewModel<SingUpViewModel.AuthState, SingUpViewModel.AuthAction>(AuthState()) {

    fun saveUserDetail(firstName: String, lastName: String, email: String, isChecked: Boolean) {
        viewModelScope.launch {
            if (!isParamsValid(firstName, lastName, email, isChecked)) return@launch

            authUseCase.saveUser(UserDetails(firstName, lastName, email)).also { result ->
                if (result is AuthUseCase.Result.DataCorrect)
                    sendAction(AuthAction.UserDetailSaved)

                if (result is AuthUseCase.Result.Error)
                    sendAction(AuthAction.Error(result.message))
            }
        }
    }

    private suspend fun isParamsValid(
        firstName: String,
        lastName: String,
        email: String,
        isChecked: Boolean
    ) = withContext(viewModelScope.coroutineContext) {
        val isFirstNameCorrect = checkParamAsync { validateFirstName(firstName) }
        val isLastNameCorrect = checkParamAsync { validateLastName(lastName) }
        val isEmailCorrect = checkParamAsync { validateEmail(email) }
        val isConfExcepted = checkParamAsync { validateConf(isChecked) }

        return@withContext isFirstNameCorrect.await()
                && isLastNameCorrect.await()
                && isConfExcepted.await()
                && isEmailCorrect.await()
    }

    private fun CoroutineScope.checkParamAsync(block: suspend () -> AuthUseCase.Result): Deferred<Boolean> {
        return async { block() == AuthUseCase.Result.DataCorrect }
    }

    fun onFirstNameChanged(firstName: String) {
        viewModelScope.launch {
            validateFirstName(firstName)
        }
    }

    fun onLastNameChanged(lastName: String) {
        viewModelScope.launch {
            validateLastName(lastName)
        }
    }

    fun onEmailChanged(email: String) {
        viewModelScope.launch {
            validateEmail(email)
        }
    }

    fun onConfCheckedChange(isChecked: Boolean) {
        viewModelScope.launch {
            validateConf(isChecked)
        }
    }

    private suspend fun validateConf(isChecked: Boolean): AuthUseCase.Result {
        return validateParam(ConfRule) {
            validateConf(isChecked)
        }
    }

    private suspend fun validateFirstName(firstName: String): AuthUseCase.Result {
        return validateParam(FirstName) {
            validateFirstName(firstName)
        }
    }

    private suspend fun validateLastName(lastName: String): AuthUseCase.Result {
        return validateParam(LastName) {
            validateLastName(lastName)
        }
    }

    private suspend fun validateEmail(email: String): AuthUseCase.Result {
        return validateParam(Email) {
            validateEmail(email)
        }
    }

    private suspend fun validateParam(
        param: AuthAction.Param,
        validationFunction: suspend AuthUseCase.() -> AuthUseCase.Result
    ): AuthUseCase.Result {
        return authUseCase.validationFunction().also { result ->
            if (result == AuthUseCase.Result.DataCorrect)
                sendAction(AuthAction.CorrectParam(param = param))
            else
                sendAction(AuthAction.WrongParam(param = param))
        }
    }

    fun navigationComplete() {
        state = state.copy(
            isSaved = false,
            isNotValidFirstName = false,
            isNotValidLastName = false,
            isNotValidEmail = false,
            isConfRuleNotExcepted = false,
            errorMessage = ""
        )
    }

    override fun onReduceState(action: AuthAction) = when (action) {
        is AuthAction.UserDetailSaved -> state.copy(
            isSaved = true,
            errorMessage = ""
        )
        is AuthAction.CorrectParam -> changeStateByParam(action.param, true)
        is AuthAction.WrongParam -> changeStateByParam(action.param, false)
        is AuthAction.Error -> state.copy(
            isSaved = false,
            errorMessage = action.message
        )
    }

    private fun changeStateByParam(param: AuthAction.Param, isCorrect: Boolean): AuthState =
        when (param) {
            is FirstName -> state.copy(isNotValidFirstName = !isCorrect)
            is LastName -> state.copy(isNotValidLastName = !isCorrect)
            is Email -> state.copy(isNotValidEmail = !isCorrect)
            is ConfRule -> state.copy(isConfRuleNotExcepted = !isCorrect)
        }

    sealed interface AuthAction : BaseAction {
        sealed interface Param

        object UserDetailSaved : AuthAction
        class CorrectParam(val param: Param) : AuthAction
        class WrongParam(val param: Param) : AuthAction
        class Error(val message: String) : AuthAction
    }

    object FirstName : AuthAction.Param
    object LastName : AuthAction.Param
    object Email : AuthAction.Param
    object ConfRule : AuthAction.Param

    data class AuthState(
        val isSaved: Boolean = false,
        val isNotValidFirstName: Boolean = false,
        val isNotValidLastName: Boolean = false,
        val isNotValidEmail: Boolean = false,
        val isConfRuleNotExcepted: Boolean = false,
        val errorMessage: String = ""
    ) : BaseState
}