package com.example.polycareer.domain.repository

import com.example.polycareer.domain.model.UserAnswer

interface ResultsRepository {
    suspend fun getAnswers(): List<UserAnswer>
}