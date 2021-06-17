package com.example.polycareer.data

import android.util.Log
import com.example.polycareer.data.database.UserDao
import com.example.polycareer.data.database.model.UserEntity
import com.example.polycareer.data.database.model.GradesEntity
import com.example.polycareer.domain.model.UserDetails
import com.example.polycareer.domain.model.UserGrades
import com.example.polycareer.domain.repository.UserRepository

const val USER_REPOSITORY_TAG = "user_repository"

class UserRepositoryImpl(
    private val userDao: UserDao
) : UserRepository {

    override suspend fun saveUserDetail(userDetails: UserDetails): Long? {
        return try {
            val userEntity = UserEntity(userDetails)
            userDao.insertUser(userEntity)
        } catch (e: Exception) {
            Log.e(USER_REPOSITORY_TAG, e.toString())
            null
        }
    }

    override suspend fun saveUserGrades(userId: Long, userGrades: UserGrades): Boolean {
        return try {
            val gradesEntity = GradesEntity(userId, userGrades)
            userDao.insertGrades(gradesEntity)
            true
        } catch (e: Exception) {
            Log.e(USER_REPOSITORY_TAG, e.toString())
            false
        }
    }

    override suspend fun checkUserEmail(email: String): Long? {
        return try {
            userDao.getIdByEmail(email).firstOrNull()
        } catch (e: Exception) {
            Log.e(USER_REPOSITORY_TAG, e.toString())
            null
        }
    }
}