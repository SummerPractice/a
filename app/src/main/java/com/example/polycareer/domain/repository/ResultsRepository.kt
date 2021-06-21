package com.example.polycareer.domain.repository

import com.example.polycareer.domain.model.UserAnswer

interface ResultsRepository {
    suspend fun getAnswers(userId: Long, tryNumber: Long): List<UserAnswer>
}