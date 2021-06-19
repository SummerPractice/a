package com.example.polycareer.data

import com.example.polycareer.data.api.ApiService
import com.example.polycareer.domain.model.QuestionsResponse
import com.example.polycareer.domain.model.Result
import retrofit2.Retrofit


class QuizItemsRemoteRepository(
    private val retrofit: Retrofit
) {
    suspend fun getQuestions(): QuestionsResponse {
        val response = retrofit.create(ApiService::class.java).getQuestionsList().execute()
        when {
            response.isSuccessful -> {
                val result = mutableListOf<List<String>>()
                for (map in response.body()!!.questions) {
                    result.add(map.values.toList())
                }
                return QuestionsResponse(Result.DataCorrect, result)
            }
            else -> {
                return QuestionsResponse(
                    Result.Error(
                        "Response is not successfull: ${response.message()}"
                    ),
                    null
                )
            }
        }
    }

}