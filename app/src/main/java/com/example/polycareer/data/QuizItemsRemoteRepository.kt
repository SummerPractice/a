package com.example.polycareer.data

import com.example.polycareer.data.api.ApiService
import com.example.polycareer.domain.model.QuestionsApiResponse
import com.example.polycareer.domain.model.Result
import retrofit2.Retrofit
import java.net.SocketTimeoutException


class QuizItemsRemoteRepository(
    private val retrofit: Retrofit
) {
    suspend fun getQuestions(): QuestionsApiResponse {
        return try {
            val response = retrofit.create(ApiService::class.java).getQuestionsList().execute()
            return when {
                response.isSuccessful -> {
                    QuestionsApiResponse(
                        Result.DataCorrect,
                        response.body()!!.quiz,
                        response.body()!!.matrix
                    )
                }
                else -> {
                    QuestionsApiResponse(
                        Result.Error("Response is not successfull: ${response.message()}"),
                        null,
                        null
                    )
                }
            }
        } catch (e: Exception) {
            QuestionsApiResponse(
                Result.Error("Lost connection to the server"),
                null,
                null
            )
        }
    }
}