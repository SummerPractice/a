package com.example.polycareer.domain.usecase

import com.example.polycareer.App
import com.example.polycareer.domain.repository.UserCache
import com.example.polycareer.domain.repository.UserRepository
import com.example.polycareer.domain.model.Result

class MainScreenUseCase(
    private val userCache: UserCache
) {
    fun checkRegister(): RegisterResult {
        return if (userCache.getCurrentUserId() != App.USER_ID_DEFAULT_VALUE) {
            RegisterResult.Registered
        } else {
            RegisterResult.NotRegistered
        }
    }

    sealed interface RegisterResult {
        object Registered : RegisterResult
        object NotRegistered : RegisterResult
    }
}
