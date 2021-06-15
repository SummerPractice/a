package com.example.polycareer.domain.repository

import com.example.polycareer.domain.model.UserDetails
import com.example.polycareer.domain.model.UserGrades

interface UserRepository {
    suspend fun saveUserDetail(userDetails: UserDetails): Boolean

    suspend fun saveUserGrades(userGrades: UserGrades): Boolean

    suspend fun checkUserEmail(email: String): Boolean
}