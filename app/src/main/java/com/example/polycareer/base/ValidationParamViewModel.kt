package com.example.polycareer.base

import com.example.polycareer.domain.usecase.ValidateParam
import com.example.polycareer.screens.auth.sign_up.SingUpViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async

abstract class ValidationParamViewModel<ViewState : BaseState, UseCase : ValidateParam>(
    private val useCase: UseCase,
    private val initialState: ViewState
) : BaseViewModel<ViewState, ValidationParamViewModel.ValidationAction>(initialState) {

    fun navigationComplete() {
        state = initialState
    }

    protected fun CoroutineScope.checkParamAsync(block: suspend () -> ValidateParam.Result) =
        async { block() == ValidateParam.Result.DataCorrect }

    protected suspend fun validateParam(
        param: ValidationAction.Param,
        validationFunction: suspend UseCase.() -> ValidateParam.Result
    ): ValidateParam.Result {
        return useCase.validationFunction().also { result ->
            if (result == ValidateParam.Result.DataCorrect)
                sendAction(ValidationAction.CorrectParam(param = param))
            else
                sendAction(ValidationAction.WrongParam(param = param))
        }
    }

    override fun onReduceState(action: ValidationAction): ViewState = when (action) {
        is ValidationAction.DataSaved -> onDataSave()
        is ValidationAction.CorrectParam -> changeStateByParam(action.param, true)
        is ValidationAction.WrongParam -> changeStateByParam(action.param, false)
        is ValidationAction.Error -> onError(action.message)
    }

    protected abstract fun changeStateByParam(
        param: ValidationAction.Param,
        isCorrect: Boolean
    ): ViewState

    protected abstract fun onDataSave(): ViewState

    protected abstract fun onError(message: String): ViewState

    sealed interface ValidationAction : BaseAction {
        interface Param

        object DataSaved : ValidationAction
        class CorrectParam(val param: Param) : ValidationAction
        class WrongParam(val param: Param) : ValidationAction
        class Error(val message: String) : ValidationAction
    }
}