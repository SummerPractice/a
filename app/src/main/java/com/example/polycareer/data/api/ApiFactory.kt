package com.example.polycareer.data.api

import com.example.polycareer.BuildConfig
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


object ApiFactory {

    private const val BASE_URL = BuildConfig.BASE_URL

    private var gson = GsonBuilder()
        .setLenient()
        .create()

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    }
}