package com.example.polycareer.domain.usecase

import com.example.polycareer.domain.model.UserDetails
import com.example.polycareer.domain.repository.AuthRepository
import com.example.polycareer.utils.isValidEmail
import com.example.polycareer.utils.isValidName

class AuthUseCase(
    private val repository: AuthRepository
) : ValidateParam {
    suspend fun saveUser(user: UserDetails): ValidateParam.Result {
        if (!repository.checkUserEmail(user.email)) {
            val isSaved = repository.saveUserDetail(user)
            if (isSaved) return ValidateParam.Result.Error("Failed to save data")
        }

        return ValidateParam.Result.DataCorrect
    }

    suspend fun validateFirstName(firstName: String): ValidateParam.Result =
        if (firstName.isValidName()) ValidateParam.Result.DataCorrect
        else ValidateParam.Result.WrongData

    suspend fun validateLastName(lastName: String): ValidateParam.Result =
        if (lastName.isValidName()) ValidateParam.Result.DataCorrect
        else ValidateParam.Result.WrongData

    suspend fun validateEmail(email: String): ValidateParam.Result =
        if (email.isValidEmail()) ValidateParam.Result.DataCorrect
        else ValidateParam.Result.WrongData

    suspend fun validateConf(isChecked: Boolean): ValidateParam.Result =
        if (isChecked) ValidateParam.Result.DataCorrect
        else ValidateParam.Result.WrongData
}