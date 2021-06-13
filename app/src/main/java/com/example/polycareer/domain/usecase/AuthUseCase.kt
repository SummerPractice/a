package com.example.polycareer.domain.usecase

import com.example.polycareer.domain.model.UserDetails
import com.example.polycareer.domain.repository.AuthRepository
import com.example.polycareer.utils.isValidEmail
import com.example.polycareer.utils.isValidName

class AuthUseCase(
    private val repository: AuthRepository
) {
    suspend fun saveUser(user: UserDetails): Result {
        if (!repository.checkUserEmail(user.email)) {
            val isSaved = repository.saveUserDetail(user)
            if (isSaved) return Result.Error("Failed to save data")
        }

        return Result.DataCorrect
    }

    suspend fun validateFirstName(firstName: String): Result  =
        if (firstName.isValidName()) Result.DataCorrect else Result.WrongData

    suspend fun validateLastName(lastName: String): Result =
        if (lastName.isValidName()) Result.DataCorrect else Result.WrongData

    suspend fun validateEmail(email: String): Result =
        if (email.isValidEmail()) Result.DataCorrect else Result.WrongData

    suspend fun validateConf(isChecked: Boolean): Result =
        if (isChecked) Result.DataCorrect else Result.WrongData

    sealed interface Result {
        object DataCorrect : Result
        object WrongData : Result
        class Error(val message: String) : Result
    }
}