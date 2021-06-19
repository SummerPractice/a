package com.example.polycareer.domain.model

data class QuestionsApiResponse (
    val result: Result,
    val quiz: List<Map<String, String>>?,
    val matrix: List<List<Double>>?
)

data class QuestionsResponse (val result: Result, val questions: List<List<String>>?)
