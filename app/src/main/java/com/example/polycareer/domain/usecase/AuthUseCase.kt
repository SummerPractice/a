package com.example.polycareer.domain.usecase

import android.content.Context
import com.example.polycareer.App
import com.example.polycareer.domain.model.UserDetails
import com.example.polycareer.domain.repository.UserRepository
import com.example.polycareer.utils.isValidEmail
import com.example.polycareer.utils.isValidName

class AuthUseCase(
    private val repository: UserRepository
) : ValidateParam() {
    suspend fun saveUser(user: UserDetails): Result {
        val userId = repository.checkUserEmail(user.email)
            ?: repository.saveUserDetail(user)
            ?: return Result.Error("Failed to save data")

        setCurrentUser(userId)
        return Result.DataCorrect
    }

    private fun setCurrentUser(userId: Long) {
        val preferences = App.applicationContext().getSharedPreferences(App.CURRENT_USER_ID, Context.MODE_PRIVATE)
        with (preferences.edit()) {
            putLong(App.USER_ID_KEY, userId)
            apply()
        }
    }

    suspend fun validateName(name: String): Result =
        validate(name.isValidName())

    suspend fun validateEmail(email: String): Result =
        validate(email.isValidEmail())

    suspend fun validateConf(isChecked: Boolean): Result =
        validate(isChecked)
}