package com.example.polycareer.domain.usecase

import com.example.polycareer.domain.model.UserDetails
import com.example.polycareer.domain.repository.AuthRepository

class AuthUseCase(
    private val repository: AuthRepository
) {
    suspend fun execute(user: UserDetails): Result {
        if (!validateEmail(user.email)) return Result.WrongData("Wrong email format")

        if (!repository.checkUserEmail(user.email)) {
            val isSaved = repository.saveUserDetail(user)
            if (isSaved) return Result.WrongData("Failed to save data")
        }

        return Result.DataCorrect
    }

    private fun validateEmail(email: String): Boolean {
        return email.contains("@")
    }

    sealed class Result {
        object DataCorrect : Result()
        class WrongData(val errorMessage: String) : Result()
    }
}