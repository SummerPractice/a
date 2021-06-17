package com.example.polycareer.domain.repository

import com.example.polycareer.domain.model.Profession

interface ProfessionRepository {
    suspend fun getProfession(id: Long): Profession
}