package com.example.polycareer.data.api

import com.example.polycareer.domain.model.DirectionInfo
import com.example.polycareer.domain.model.ProfessionInfo
import com.example.polycareer.domain.model.QuestionsApiResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("questions")
    fun getQuestionsList() : Call<QuestionsApiResponse>

    @GET("directions")
    fun getDirectionsList() : Call<List<DirectionInfo>>

    @GET("professions")
    fun getProfessionsList() : Call<List<ProfessionInfo>>
}