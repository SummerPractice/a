package com.example.polycareer.data

import com.example.polycareer.domain.model.ProfessionInfo
import com.example.polycareer.domain.repository.ProfessionRepository

class ProfessionsRepositoryTest : ProfessionRepository {
    override suspend fun getProfession(id: Long): ProfessionInfo {
        return when(id) {
            1L -> ProfessionInfo(1, "Профессия 1", 25)
            2L -> ProfessionInfo(1, "Профессия 2", 25)
            3L -> ProfessionInfo(3, "Профессия 3", 25)
            4L -> ProfessionInfo(1, "Профессия 4", 25)
            5L -> ProfessionInfo(5, "Профессия 5", 25)
            6L -> ProfessionInfo(5, "Профессия 6", 25)
            7L -> ProfessionInfo(7, "Профессия 7", 25)
            8L -> ProfessionInfo(7, "Профессия 8", 25)
            else -> ProfessionInfo(0, "Тестовая профессия", 25)
        }
    }
}