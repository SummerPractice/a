package com.example.polycareer.screens.auth.sign_up

import androidx.lifecycle.viewModelScope
import com.example.polycareer.base.BaseState
import com.example.polycareer.base.BaseViewModel
import com.example.polycareer.base.ValidationParamViewModel
import com.example.polycareer.domain.model.UserDetails
import com.example.polycareer.domain.usecase.AuthUseCase
import com.example.polycareer.domain.usecase.ValidateParam
import kotlinx.coroutines.*


class SingUpViewModel(
    private val authUseCase: AuthUseCase
) : ValidationParamViewModel<SingUpViewModel.AuthState, AuthUseCase>(authUseCase, AuthState()) {

    fun saveUserDetail(firstName: String, lastName: String, email: String, isChecked: Boolean) {
        viewModelScope.launch {
            if (!isParamsValid(firstName, lastName, email, isChecked)) return@launch

            authUseCase.saveUser(UserDetails(firstName, lastName, email)).also { result ->
                if (result is ValidateParam.Result.DataCorrect)
                    sendAction(ValidationAction.DataSaved)

                if (result is ValidateParam.Result.Error)
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

    private suspend fun validateFirstName(firstName: String): ValidateParam.Result {
        return validateParam(AuthParam.FirstName) {
            validateFirstName(firstName)
        }
    }

    private suspend fun validateLastName(lastName: String): ValidateParam.Result {
        return validateParam(AuthParam.LastName) {
            validateLastName(lastName)
        }
    }

    private suspend fun validateEmail(email: String): ValidateParam.Result {
        return validateParam(AuthParam.Email) {
            validateEmail(email)
        }
    }

    private suspend fun validateConf(isChecked: Boolean): ValidateParam.Result {
        return validateParam(AuthParam.ConfRule) {
            validateConf(isChecked)
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
            is AuthParam.FirstName -> state.copy(isNotValidFirstName = !isCorrect)
            is AuthParam.LastName -> state.copy(isNotValidLastName = !isCorrect)
            is AuthParam.Email -> state.copy(isNotValidEmail = !isCorrect)
            is AuthParam.ConfRule -> state.copy(isConfRuleNotExcepted = !isCorrect)
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