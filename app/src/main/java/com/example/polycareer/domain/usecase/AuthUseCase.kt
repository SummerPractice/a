package com.example.polycareer.domain.usecase

import com.example.polycareer.domain.model.UserDetails
import com.example.polycareer.domain.repository.AuthRepository
import com.example.polycareer.utils.isValidEmail

class AuthUseCase(
    private val repository: AuthRepository
) {
    suspend fun execute(user: UserDetails): Result {
        if (!user.email.isValidEmail()) return Result.WrongData("Wrong email format")

        if (!repository.checkUserEmail(user.email)) {
            val isSaved = repository.saveUserDetail(user)
            if (isSaved) return Result.WrongData("Failed to save data")
        }

        return Result.DataCorrect
    }

    sealed class Result {
        object DataCorrect : Result()
        class WrongData(val errorMessage: String) : Result()
    }
}