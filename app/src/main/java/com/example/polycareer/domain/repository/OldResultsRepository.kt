package com.example.polycareer.domain.repository

import com.example.polycareer.domain.model.UserResult

interface OldResultsRepository {
    suspend fun getResults(): List<UserResult>
}