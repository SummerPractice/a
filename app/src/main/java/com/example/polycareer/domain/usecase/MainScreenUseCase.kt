package com.example.polycareer.domain.usecase

import com.example.polycareer.App
import com.example.polycareer.domain.repository.UserCache

class MainScreenUseCase(
    private val userCache: UserCache
) {
    fun checkRegister(): Result {
        return if (userCache.getCurrentUserId() != App.USER_ID_DEFAULT_VALUE) {
            Result.Registered
        } else {
            Result.NotRegistered
        }
    }


    sealed interface Result {
        object Registered : Result
        object NotRegistered : Result
    }
}
