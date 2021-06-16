package com.example.polycareer.data

import com.example.polycareer.data.database.UserDao
import com.example.polycareer.data.database.model.UserDetailsEntity
import com.example.polycareer.data.database.model.UserGradesEntity
import com.example.polycareer.domain.model.UserDetails
import com.example.polycareer.domain.model.UserGrades
import com.example.polycareer.domain.repository.UserRepository

class UserRepositoryImpl(
    private val userDao: UserDao
) : UserRepository {

    override suspend fun saveUserDetail(userDetails: UserDetails): Long? {
        return try {
            val userEntity = UserDetailsEntity(userDetails)
            userDao.insertUser(userEntity)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun saveUserGrades(userId: Long, userGrades: UserGrades): Boolean {
        return try {
            val gradesEntity = UserGradesEntity(userId, userGrades)
            userDao.insertGrades(gradesEntity)
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun checkUserEmail(email: String): Long? {
        return try {
            userDao.getIdByEmail(email).firstOrNull()
        } catch (e: Exception) {
            null
        }
    }
}