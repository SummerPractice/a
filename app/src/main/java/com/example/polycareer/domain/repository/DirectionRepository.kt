package com.example.polycareer.domain.repository

import com.example.polycareer.domain.model.DirectionInfo

interface DirectionRepository {
    suspend fun getDirection(id: Long) : DirectionInfo
}