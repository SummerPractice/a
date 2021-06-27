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
    var user: UserDetails? = null
    var isNewsChecked: Boolean = false
    var userId: Long = App.USER_ID_DEFAULT_VALUE

    suspend fun saveUser(user: UserDetails, isNewsChecked: Boolean): Result {
        userId = localRepository.checkUserEmail(user.email)
            ?: localRepository.saveUserDetail(user)
                    ?: return Result.Error("Failed to save data")
        this.user = user
        this.isNewsChecked = isNewsChecked
        return Result.DataCorrect
    }


    suspend fun saveGrades(grades: UserGrades): Result {
        localRepository.setCurrentUser(userId)
        return if (userId != App.USER_ID_DEFAULT_VALUE
            && localRepository.saveUserGrades(userId, grades)
        ) {
            Result.DataCorrect
        } else {
            Result.Error("Failed to save data")
        }
    }

    suspend fun sendUserInfo(grades: UserGrades): Result = withContext(Dispatchers.IO) {
        try {
            if (isNewsChecked) {
                remoteRepository.saveUser(user!!, grades)
            }
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