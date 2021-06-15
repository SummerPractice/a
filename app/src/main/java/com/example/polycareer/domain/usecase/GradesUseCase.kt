package com.example.polycareer.domain.usecase

import com.example.polycareer.domain.model.UserGrades
import com.example.polycareer.domain.repository.UserRepository
import com.example.polycareer.utils.isValidExamGrade
import com.example.polycareer.utils.isValidIdGrade

class GradesUseCase(
    private val repository: UserRepository
) : ValidateParam() {

    suspend fun saveGrades(grades: UserGrades): Result =
        if (repository.saveUserGrades(grades)) Result.DataCorrect
        else Result.Error("Failed to save data")

    suspend fun validateExamGrade(grade: String) = validate(grade.isValidExamGrade())

    suspend fun validateIdGrade(grade: String) = validate(grade.isValidIdGrade())
}