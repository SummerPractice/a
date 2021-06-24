package com.example.polycareer.domain.repository

import com.example.polycareer.domain.model.UserDetails
import com.example.polycareer.domain.model.UserGrades

interface UserRepository : UserCache {
    suspend fun saveUserDetail(userDetails: UserDetails): Long?

    suspend fun saveUserGrades(userId: Long, userGrades: UserGrades): Boolean

    suspend fun checkUserEmail(email: String): Long?

    suspend fun saveDefaultUser(): Boolean
}