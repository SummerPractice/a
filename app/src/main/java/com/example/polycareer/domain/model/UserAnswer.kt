package com.example.polycareer.domain.model

data class UserAnswer(
    val professionNumber: Long,
    val score: Map<String, Double>
)