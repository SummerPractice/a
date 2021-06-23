package com.example.polycareer.domain.usecase

import com.example.polycareer.App
import com.example.polycareer.domain.model.UserGrades
import com.example.polycareer.domain.repository.UserRepository
import com.example.polycareer.utils.isValidExamGrade
import com.example.polycareer.utils.isValidIdGrade
import com.example.polycareer.domain.model.Result


class GradesUseCase(
    private val repository: UserRepository
) : ValidateParam() {

    suspend fun saveGrades(grades: UserGrades): Result {
        val userId = repository.getCurrentUserId()
        return if (userId != App.USER_ID_DEFAULT_VALUE
            && repository.saveUserGrades(userId, grades)
        ) {
            Result.DataCorrect
        } else {
            Result.Error("Failed to save data")
        }
    }

    suspend fun validateExamGrade(grade: String) = validate(grade.isValidExamGrade())

    suspend fun validateExamGrade(firstGrade: String, secondGrade: String) =
        validate(firstGrade.isValidExamGrade() || secondGrade.isValidExamGrade())

    suspend fun validateIdGrade(grade: String) = validate(grade.isValidIdGrade())
}