package com.example.polycareer.base

import com.example.polycareer.domain.usecase.ValidateParam
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import com.example.polycareer.domain.model.Result
import com.example.polycareer.utils.isValidExamGrade

abstract class ValidationParamViewModel<ViewState : BaseState, UseCase : ValidateParam>(
    private val useCase: UseCase,
    private val initialState: ViewState
) : BaseViewModel<ViewState, ValidationParamViewModel.ValidationAction>(initialState) {

    fun navigationComplete() {
        state = initialState
    }

    protected fun CoroutineScope.checkParamAsync(block: suspend () -> Result) =
        async { block() == Result.DataCorrect }

    protected suspend fun validateParam(
        param: ValidationAction.Param,
        validationFunction: suspend UseCase.() -> Result
    ): Result {
        return useCase.validationFunction().also { result ->
            if (result == Result.DataCorrect)
                sendAction(ValidationAction.CorrectParam(param = param))
            else
                sendAction(ValidationAction.WrongParam(param = param))
        }
    }

    protected suspend fun validateParam(
        firstParam: ValidationAction.Param,
        secondParam: ValidationAction.Param,
        firstString: String,
        secondString: String,
        validationFunction: suspend UseCase.() -> Result
    ): Result {
        return useCase.validationFunction().also { result ->
            if (result == Result.DataCorrect) {
                sendAction(ValidationAction.CorrectParam(param = firstParam))
                sendAction(ValidationAction.CorrectParam(param = secondParam))
            }
            else {
                if (!firstString.isValidExamGrade()) {
                    sendAction(ValidationAction.WrongParam(param = firstParam))
                }
                if (!secondString.isValidExamGrade()) {
                    sendAction(ValidationAction.WrongParam(param = secondParam))
                }
            }
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