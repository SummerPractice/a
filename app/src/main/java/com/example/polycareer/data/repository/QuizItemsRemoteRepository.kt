package com.example.polycareer.data.repository

import com.example.polycareer.data.api.ApiService
import com.example.polycareer.domain.model.QuestionsApiResponse
import com.example.polycareer.exception.LostConnectionException
import com.example.polycareer.exception.WrongResponseException
import retrofit2.Retrofit

class QuizItemsRemoteRepository(
    private val retrofit: Retrofit
) {
    fun getQuestions(): QuestionsApiResponse {
        val response = getResponse()

        if (response.isSuccessful) {
            return QuestionsApiResponse(
                response.body()!!.quiz,
                response.body()!!.matrix,
                response.body()!!.prof
            )
        } else throw WrongResponseException(response.message())
    }

    private fun getResponse() = try {
        retrofit.create(ApiService::class.java).getQuestionsList().execute()
    } catch (e: Exception) {
        throw LostConnectionException()
    }
}