package com.example.polycareer.data.api

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET(".")
    fun getQuestionsList() : Call<QuestionsResponse>
}