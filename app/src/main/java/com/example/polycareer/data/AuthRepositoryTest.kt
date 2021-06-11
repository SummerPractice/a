package com.example.polycareer.data

import com.example.polycareer.domain.model.UserDetails
import com.example.polycareer.domain.repository.AuthRepository

class AuthRepositoryTest : AuthRepository {
    override suspend fun saveUserDetail(userDetails: UserDetails): Boolean {
        return true
    }

    override suspend fun checkUserEmail(email: String): Boolean {
        return true
    }
}