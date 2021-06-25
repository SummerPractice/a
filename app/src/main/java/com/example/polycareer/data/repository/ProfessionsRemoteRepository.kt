package com.example.polycareer.data.repository

import com.example.polycareer.data.api.ApiService
import com.example.polycareer.domain.model.ProfessionInfo
import com.example.polycareer.exception.LostConnectionException
import com.example.polycareer.exception.WrongResponseException
import retrofit2.Retrofit

class ProfessionsRemoteRepository(
    private val retrofit: Retrofit
) {
    fun getAllProfessions(): List<ProfessionInfo> {
        val response = getResponse()

        if (response.isSuccessful) {
            return response.body()!!
        } else throw WrongResponseException(response.message())
    }

    private fun getResponse() = try {
        retrofit.create(ApiService::class.java).getProfessionsList().execute()
    } catch (e: Exception) {
        throw LostConnectionException()
    }
}
