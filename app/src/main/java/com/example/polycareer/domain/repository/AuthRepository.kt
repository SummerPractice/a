package com.example.polycareer.domain.repository

import com.example.polycareer.domain.model.UserDetails

interface AuthRepository {
    suspend fun saveUserDetail(userDetails: UserDetails): Boolean

    suspend fun checkUserEmail(email: String): Boolean
}