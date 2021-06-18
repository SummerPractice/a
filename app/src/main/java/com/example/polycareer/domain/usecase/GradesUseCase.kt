package com.example.polycareer.domain.usecase

import android.content.Context
import com.example.polycareer.App
import com.example.polycareer.domain.model.UserGrades
import com.example.polycareer.domain.repository.UserRepository
import com.example.polycareer.utils.isValidExamGrade
import com.example.polycareer.utils.isValidIdGrade
import com.example.polycareer.domain.model.Result
import com.example.polycareer.utils.getCurrentUserId


class GradesUseCase(
    private val repository: UserRepository
) : ValidateParam() {

    suspend fun saveGrades(grades: UserGrades): Result {
        val userId = getCurrentUserId()
        return if (userId != App.USER_ID_DEFAULT_VALUE
            && repository.saveUserGrades(userId, grades)
        ) {
            Result.DataCorrect
        } else {
            Result.Error("Failed to save data")
        }
    }

    suspend fun validateExamGrade(grade: String) = validate(grade.isValidExamGrade())

    suspend fun validateIdGrade(grade: String) = validate(grade.isValidIdGrade())
}