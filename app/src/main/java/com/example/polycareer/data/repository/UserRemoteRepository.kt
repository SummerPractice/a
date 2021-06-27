package com.example.polycareer.data.repository

import com.example.polycareer.data.api.ApiFactory.retrofit
import com.example.polycareer.data.api.ApiService
import com.example.polycareer.domain.model.UserDetails
import com.example.polycareer.domain.model.UserGrades
import com.example.polycareer.domain.model.UserPostInfo
import com.example.polycareer.exception.LostConnectionException
import com.example.polycareer.exception.WrongResponseException
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import retrofit2.Response
import retrofit2.Retrofit

class UserRemoteRepository(
    retrofit: Retrofit
) {
    suspend fun saveUser(user: UserDetails, grades: UserGrades) {
        val userInfo = UserPostInfo(
            firstname = user.name,
            lastname = user.surname,
            email = user.email,
            math = grades.mathematics.toInt(),
            rus = grades.russian.toInt(),
            phys = if (grades.physics.isNotEmpty()) grades.physics.toInt() else 0,
            inf = if (grades.informatics.isNotEmpty()) grades.informatics.toInt() else 0,
            individual = if (grades.individualAchievements.isNotEmpty()) grades.individualAchievements.toInt() else 0
        )
        val response = getResponse(userInfo)
        if (response != null && !response.isSuccessful) {
            throw WrongResponseException(response.message())
        }
    }

    private fun getResponse(userInfo: UserPostInfo) = try {
        retrofit.create(ApiService::class.java).postUserInfo(userInfo).execute()
    } catch (e: Exception) {
        if (e !is JsonSyntaxException) {
            throw LostConnectionException()
        }
        null
    }
}
