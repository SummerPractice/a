package com.example.polycareer.domain.repository

import com.example.polycareer.domain.model.ProfessionInfo

interface ProfessionRepository {
    suspend fun getProfession(id: Long): ProfessionInfo
}