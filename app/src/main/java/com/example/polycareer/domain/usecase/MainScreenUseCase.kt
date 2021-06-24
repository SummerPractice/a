package com.example.polycareer.domain.usecase

import com.example.polycareer.App
import com.example.polycareer.domain.repository.UserCache
import com.example.polycareer.domain.repository.UserRepository
import com.example.polycareer.domain.model.Result

class MainScreenUseCase(
    private val userCache: UserCache,
    private val repository: UserRepository
) {
    fun checkRegister(): RegisterResult {
        return if (userCache.getCurrentUserId() != App.USER_ID_DEFAULT_VALUE) {
            RegisterResult.Registered
        } else {
            RegisterResult.NotRegistered
        }
    }

    suspend fun saveDefaultUser(): Result {
        return if (repository.saveDefaultUser()) {
            repository.setCurrentUser(App.USER_ID_DEFAULT_VALUE)
            Result.DataCorrect
        } else {
            Result.WrongData
        }
    }

    sealed interface RegisterResult {
        object Registered : RegisterResult
        object NotRegistered : RegisterResult
    }
}
