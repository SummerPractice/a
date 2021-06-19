package com.example.polycareer.data.api

import com.example.polycareer.domain.model.QuestionsApiResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET(".")
    fun getQuestionsList() : Call<QuestionsApiResponse>
}