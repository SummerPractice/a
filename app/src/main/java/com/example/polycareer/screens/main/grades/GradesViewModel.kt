package com.example.polycareer.screens.main.grades

import androidx.lifecycle.viewModelScope
import com.example.polycareer.base.BaseState
import com.example.polycareer.base.ValidationParamViewModel
import com.example.polycareer.domain.model.UserGrades
import com.example.polycareer.domain.usecase.GradesUseCase
import com.example.polycareer.domain.usecase.ValidateParam
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GradesViewModel(
    private val useCase: GradesUseCase
) : ValidationParamViewModel<GradesViewModel.GradesState, GradesUseCase>(useCase, GradesState()) {

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
                if (result is ValidateParam.Result.DataCorrect)
                    sendAction(ValidationAction.DataSaved)

                if (result is ValidateParam.Result.Error)
                    sendAction(ValidationAction.Error(result.message))
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
        val isPhysGradeCorrect = checkParamAsync { validatePhys(phys) }
        val isInfGradeCorrect = checkParamAsync { validateInf(inf) }
        val isIdGradeCorrect = checkParamAsync { validateId(id) }

        return@withContext isMathGradeCorrect.await()
                && isRusGradeCorrect.await()
                && isPhysGradeCorrect.await()
                && isInfGradeCorrect.await()
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

    fun onPhysGradeChange(phys: String) {
        viewModelScope.launch {
            validatePhys(phys)
        }
    }

    fun onInfGradeChange(inf: String) {
        viewModelScope.launch {
            validateInf(inf)
        }
    }

    fun onIdGradeChange(id: String) {
        viewModelScope.launch {
            validateId(id)
        }
    }

    private suspend fun validateMath(math: String): ValidateParam.Result {
        return validateParam(GradesParam.Math) {
            validateExamGrade(math)
        }
    }

    private suspend fun validateRus(rus: String): ValidateParam.Result {
        return validateParam(GradesParam.Rus) {
            validateExamGrade(rus)
        }
    }

    private suspend fun validatePhys(phys: String): ValidateParam.Result {
        return validateParam(GradesParam.Phys) {
            validateExamGrade(phys)
        }
    }

    private suspend fun validateInf(inf: String): ValidateParam.Result {
        return validateParam(GradesParam.Inf) {
            validateExamGrade(inf)
        }
    }

    private suspend fun validateId(id: String): ValidateParam.Result {
        return validateParam(GradesParam.Id) {
            validateIdGrade(id)
        }
    }

    fun navigationComplete() {
        state = GradesState()
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
            is GradesParam.Math -> state.copy(isNotValidMath = !isCorrect)
            is GradesParam.Rus -> state.copy(isNotValidRus = !isCorrect)
            is GradesParam.Phys -> state.copy(isNotValidPhys = !isCorrect)
            is GradesParam.Inf -> state.copy(isNotValidInf = !isCorrect)
            is GradesParam.Id -> state.copy(isNotValidId = !isCorrect)
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