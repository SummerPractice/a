package com.example.polycareer.data

import com.example.polycareer.data.database.UserDao
import com.example.polycareer.data.database.model.UserDetailsEntity
import com.example.polycareer.domain.model.UserDetails
import com.example.polycareer.domain.model.UserGrades
import com.example.polycareer.domain.repository.UserRepository

class UserRepositoryImpl(
    private val userDao: UserDao
) : UserRepository {

    override suspend fun saveUserDetail(userDetails: UserDetails): Boolean {
        return try {
            val userEntity = UserDetailsEntity(userDetails)
            userDao.insertUser(userEntity)
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun saveUserGrades(userGrades: UserGrades): Boolean {
        return true
    }

    override suspend fun checkUserEmail(email: String): Boolean {
        try {
            userDao.getIdByEmail(email).firstOrNull() ?: return false
            return true
        } catch (e: Exception) {
            return false
        }
    }
}