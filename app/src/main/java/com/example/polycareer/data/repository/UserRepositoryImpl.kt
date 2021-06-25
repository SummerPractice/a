package com.example.polycareer.data.repository

import android.content.Context
import android.util.Log
import com.example.polycareer.App
import com.example.polycareer.data.database.dao.UserDao
import com.example.polycareer.data.database.model.GradesEntity
import com.example.polycareer.data.database.model.UserEntity
import com.example.polycareer.domain.model.UserDetails
import com.example.polycareer.domain.model.UserGrades
import com.example.polycareer.domain.repository.UserRepository


class UserRepositoryImpl(
    private val userDao: UserDao,
    private val applicationContext: Context
) : UserRepository {
    companion object {
        const val USER_REPOSITORY_TAG = "user_repository"
    }

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

    override fun getCurrentUserId(): Long {
        val preferences =
            applicationContext.getSharedPreferences(App.CURRENT_USER_ID, Context.MODE_PRIVATE)
        return preferences.getLong(App.USER_ID_KEY, App.USER_ID_DEFAULT_VALUE)
    }

    override fun setCurrentUser(userId: Long) {
        val preferences =
            applicationContext.getSharedPreferences(App.CURRENT_USER_ID, Context.MODE_PRIVATE)

        with(preferences.edit()) {
            putLong(App.USER_ID_KEY, userId)
            apply()
        }
    }
}