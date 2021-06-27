package com.example.polycareer.screens.auth.sign_up

import androidx.lifecycle.viewModelScope
import com.example.polycareer.base.BaseState
import com.example.polycareer.base.ValidationParamViewModel
import com.example.polycareer.domain.model.Result
import com.example.polycareer.domain.model.UserDetails
import com.example.polycareer.domain.usecase.AuthUseCase
import kotlinx.coroutines.*


class SingUpViewModel(
    private val authUseCase: AuthUseCase
) : ValidationParamViewModel<SingUpViewModel.AuthState, AuthUseCase>(authUseCase, AuthState()) {

    fun saveUserDetail(firstName: String, lastName: String, email: String, isConfChecked: Boolean, isNewsChecked: Boolean) {
        viewModelScope.launch {
            if (!isParamsValid(firstName, lastName, email, isConfChecked)) return@launch

            authUseCase.saveUser(UserDetails(firstName, lastName, email), isNewsChecked).also { result ->
                if (result is Result.DataCorrect)
                    sendAction(ValidationAction.DataSaved)

                if (result is Result.Error)
                    sendAction(ValidationAction.Error(result.message))
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

    private suspend fun validateFirstName(firstName: String): Result {
        return validateParam(AuthParam.FirstName) {
            validateName(firstName)
        }
    }

    private suspend fun validateLastName(lastName: String): Result {
        return validateParam(AuthParam.LastName) {
            validateName(lastName)
        }
    }

    private suspend fun validateEmail(email: String): Result {
        return validateParam(AuthParam.Email) {
            validateEmail(email)
        }
    }

    private suspend fun validateConf(isChecked: Boolean): Result {
        return validateParam(AuthParam.ConfRule) {
            validateConf(isChecked)
        }
    }

    override fun onDataSave() = state.copy(
        isSaved = true,
        errorMessage = ""
    )

    override fun onError(message: String) = state.copy(
        isSaved = false,
        errorMessage = message
    )

    override fun changeStateByParam(param: ValidationAction.Param, isCorrect: Boolean): AuthState {
        return when (param as AuthParam) {
            is AuthParam.FirstName -> state.copy(isNotValidFirstName = !isCorrect, errorMessage = "")
            is AuthParam.LastName -> state.copy(isNotValidLastName = !isCorrect, errorMessage = "")
            is AuthParam.Email -> state.copy(isNotValidEmail = !isCorrect, errorMessage = "")
            is AuthParam.ConfRule -> state.copy(isConfRuleNotExcepted = !isCorrect, errorMessage = "")
        }
    }

    sealed interface AuthParam : ValidationAction.Param {
        object FirstName : AuthParam
        object LastName : AuthParam
        object Email : AuthParam
        object ConfRule : AuthParam
    }

    data class AuthState(
        val isSaved: Boolean = false,
        val isNotValidFirstName: Boolean = false,
        val isNotValidLastName: Boolean = false,
        val isNotValidEmail: Boolean = false,
        val isConfRuleNotExcepted: Boolean = false,
        val errorMessage: String = ""
    ) : BaseState
}