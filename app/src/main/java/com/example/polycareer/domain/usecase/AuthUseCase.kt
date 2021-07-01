package com.example.polycareer.domain.usecase

import com.example.polycareer.App
import com.example.polycareer.data.repository.UserRemoteRepository
import com.example.polycareer.domain.model.Result
import com.example.polycareer.domain.model.UserDetails
import com.example.polycareer.domain.model.UserGrades
import com.example.polycareer.domain.repository.UserRepository
import com.example.polycareer.exception.LostConnectionException
import com.example.polycareer.exception.WrongResponseException
import com.example.polycareer.utils.isValidEmail
import com.example.polycareer.utils.isValidExamGrade
import com.example.polycareer.utils.isValidIdGrade
import com.example.polycareer.utils.isValidName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthUseCase(
    private val localRepository: UserRepository,
    private val remoteRepository: UserRemoteRepository
) : ValidateParam() {
    private lateinit var user: UserDetails
    private var isNewsChecked: Boolean = false

    fun saveUser(user: UserDetails, isNewsChecked: Boolean): Result {
        this.user = user
        this.isNewsChecked = isNewsChecked
        return Result.DataCorrect
    }

    suspend fun saveGrades(grades: UserGrades): Result {
        val resultOfSaveUserOnServer = sendUserInfo(grades)
        if (resultOfSaveUserOnServer is Result.Error) return resultOfSaveUserOnServer

        val userId = localRepository.checkUserEmail(user.email)
            ?: localRepository.saveUserDetail(user)
                    ?: App.USER_ID_DEFAULT_VALUE

        return if (userId != App.USER_ID_DEFAULT_VALUE
            && localRepository.saveUserGrades(userId, grades)
        ) {
            localRepository.setCurrentUser(userId)
            Result.DataCorrect
        } else {
            Result.Error("Failed to save data")
        }
    }

    private suspend fun sendUserInfo(grades: UserGrades): Result = withContext(Dispatchers.IO) {
        try {
            if (isNewsChecked) remoteRepository.saveUser(user, grades)
            Result.DataCorrect
        } catch (e: LostConnectionException) {
            Result.Error("Lost connection with server")
        } catch (e: WrongResponseException) {
            Result.Error("Wrong response from server")
        }
    }

    suspend fun validateExamGrade(grade: String) = validate(grade.isValidExamGrade())

    suspend fun validateExamGrade(firstGrade: String, secondGrade: String) =
        validate(firstGrade.isValidExamGrade() || secondGrade.isValidExamGrade())

    suspend fun validateIdGrade(grade: String) = validate(grade.isValidIdGrade())

    suspend fun validateName(name: String): Result =
        validate(name.isValidName())

    suspend fun validateEmail(email: String): Result =
        validate(email.isValidEmail())

    suspend fun validateConf(isChecked: Boolean): Result =
        validate(isChecked)
}