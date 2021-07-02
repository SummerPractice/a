package com.example.polycareer.screens.auth.grades

import androidx.lifecycle.viewModelScope
import com.example.polycareer.base.BaseState
import com.example.polycareer.base.ValidationParamViewModel
import com.example.polycareer.domain.model.UserGrades
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.polycareer.domain.model.Result
import com.example.polycareer.domain.usecase.AuthUseCase

class GradesViewModel(
    private val useCase: AuthUseCase
) : ValidationParamViewModel<GradesViewModel.GradesState, AuthUseCase>(useCase, GradesState()) {

    fun saveGrades(
        math: String,
        rus: String,
        phys: String,
        inf: String,
        id: String
    ) {
        viewModelScope.launch {
            if (!isParamsValid(math, rus, phys, inf, id)) return@launch

            useCase.saveGrades(UserGrades(math, rus, phys, inf, id)).also { result ->
                if (result is Result.Error)
                    sendAction(ValidationAction.Error(result.message))

                if (result is Result.DataCorrect)
                    sendAction(ValidationAction.DataSaved)
            }
        }
    }

    private suspend fun isParamsValid(
        math: String,
        rus: String,
        phys: String,
        inf: String,
        id: String
    ) = withContext(viewModelScope.coroutineContext) {
        val isMathGradeCorrect = checkParamAsync { validateMath(math) }
        val isRusGradeCorrect = checkParamAsync { validateRus(rus) }
        val isPhysGradeCorrect = checkParamAsync { validatePhysAndInf(phys, inf) }
        val isInfGradeCorrect = checkParamAsync { validatePhysAndInf(phys, inf) }
        val isIdGradeCorrect = checkParamAsync { validateId(id) }

        return@withContext isMathGradeCorrect.await()
                && isRusGradeCorrect.await()
                && (isPhysGradeCorrect.await() || isInfGradeCorrect.await())
                && isIdGradeCorrect.await()
    }

    fun onMathGradeChange(math: String) {
        viewModelScope.launch {
            validateMath(math)
        }
    }

    fun onRusGradeChange(rus: String) {
        viewModelScope.launch {
            validateRus(rus)
        }
    }

    fun onPhysGradeChange(phys: String, inf: String) {
        viewModelScope.launch {
            validatePhysAndInf(phys, inf)
        }
    }

    fun onInfGradeChange(phys: String, inf: String) {
        viewModelScope.launch {
            validatePhysAndInf(phys, inf)
        }
    }

    fun onIdGradeChange(id: String) {
        viewModelScope.launch {
            validateId(id)
        }
    }

    private suspend fun validateMath(math: String): Result {
        return validateParam(GradesParam.Math) {
            validateExamGrade(math)
        }
    }

    private suspend fun validateRus(rus: String): Result {
        return validateParam(GradesParam.Rus) {
            validateExamGrade(rus)
        }
    }

    private suspend fun validatePhysAndInf(phys: String, inf: String): Result {
        return validateParam(GradesParam.Phys, GradesParam.Inf, phys, inf) {
            validateExamGrade(phys, inf)
        }
    }

    private suspend fun validateId(id: String): Result {
        return validateParam(GradesParam.Id) {
            validateIdGrade(id)
        }
    }

    override fun onDataSave(): GradesState = state.copy(
        isSaved = true,
        errorMessage = ""
    )

    override fun onError(message: String): GradesState = state.copy(
        isSaved = false,
        errorMessage = message
    )

    override fun changeStateByParam(
        param: ValidationAction.Param,
        isCorrect: Boolean
    ): GradesState {
        return when (param as GradesParam) {
            is GradesParam.Math -> state.copy(isNotValidMath = !isCorrect, errorMessage = "")
            is GradesParam.Rus -> state.copy(isNotValidRus = !isCorrect, errorMessage = "")
            is GradesParam.Phys -> state.copy(isNotValidPhys = !isCorrect, errorMessage = "")
            is GradesParam.Inf -> state.copy(isNotValidInf = !isCorrect, errorMessage = "")
            is GradesParam.Id -> state.copy(isNotValidId = !isCorrect, errorMessage = "")
        }
    }

    sealed interface GradesParam : ValidationAction.Param {
        object Math : GradesParam
        object Rus : GradesParam
        object Phys : GradesParam
        object Inf : GradesParam
        object Id : GradesParam
    }

    data class GradesState(
        val isSaved: Boolean = false,
        val isNotValidMath: Boolean = false,
        val isNotValidRus: Boolean = false,
        val isNotValidPhys: Boolean = false,
        val isNotValidInf: Boolean = false,
        val isNotValidId: Boolean = false,
        val errorMessage: String = ""
    ) : BaseState
}