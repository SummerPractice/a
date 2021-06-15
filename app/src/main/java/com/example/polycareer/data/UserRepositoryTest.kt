package com.example.polycareer.data

import com.example.polycareer.domain.model.UserDetails
import com.example.polycareer.domain.model.UserGrades
import com.example.polycareer.domain.repository.UserRepository

class UserRepositoryTest : UserRepository {
    override suspend fun saveUserDetail(userDetails: UserDetails): Boolean {
        return true
    }

    override suspend fun saveUserGrades(userGrades: UserGrades): Boolean {
        return true
    }

    override suspend fun checkUserEmail(email: String): Boolean {
        return true
    }
}