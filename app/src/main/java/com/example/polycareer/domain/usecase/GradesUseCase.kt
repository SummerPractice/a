package com.example.polycareer.domain.usecase

import android.content.Context
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
        val userId = getCurrentUserId()
        return if (userId != App.USER_ID_DEFAULT_VALUE
            && repository.saveUserGrades(userId, grades)
        ) {
            Result.DataCorrect
        } else {
            Result.Error("Failed to save data")
        }
    }

    private fun getCurrentUserId(): Long {
        val preferences =
            App.applicationContext().getSharedPreferences(App.CURRENT_USER_ID, Context.MODE_PRIVATE)
        return preferences.getLong(App.USER_ID_KEY, App.USER_ID_DEFAULT_VALUE)
    }

    suspend fun validateExamGrade(grade: String) = validate(grade.isValidExamGrade())

    suspend fun validateIdGrade(grade: String) = validate(grade.isValidIdGrade())
}