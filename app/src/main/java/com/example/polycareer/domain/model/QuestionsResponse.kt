package com.example.polycareer.domain.model

data class QuestionsApiResponse (
    val quiz: List<Map<String, String>>,
    val matrix: List<List<Double>>,
    val prof: List<Long>
)

data class QuestionsResponse(val questions: List<List<String>>)
