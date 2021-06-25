package com.example.polycareer.data.repository

import com.example.polycareer.data.api.ApiService
import com.example.polycareer.domain.model.DirectionsApiResponse
import com.example.polycareer.exception.LostConnectionException
import com.example.polycareer.exception.WrongResponseException
import retrofit2.Retrofit

class DirectionsRemoteRepository(
    private val retrofit: Retrofit
) {
    fun getAllDirections(): DirectionsApiResponse {
        val response = getResponse()

        if (response.isSuccessful) {
            return DirectionsApiResponse(
                response.body()!!
            )
        } else throw WrongResponseException(response.message())
    }

    private fun getResponse() = try {
        retrofit.create(ApiService::class.java).getDirectionsList().execute()
    } catch (e: Exception) {
        throw LostConnectionException()
    }

}
