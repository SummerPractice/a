package com.example.polycareer.domain.repository

import com.example.polycareer.domain.model.UserResultInfo

interface ResultsInfoRepository {
    suspend fun getResults(): List<UserResultInfo>
}