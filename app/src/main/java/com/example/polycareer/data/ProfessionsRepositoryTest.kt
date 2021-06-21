package com.example.polycareer.data

import com.example.polycareer.domain.model.Profession
import com.example.polycareer.domain.repository.ProfessionRepository

class ProfessionsRepositoryTest : ProfessionRepository {
    override suspend fun getProfession(id: Long): Profession {
        return when(id) {
            1L -> Profession(1, "Профессия 1", 25)
            2L -> Profession(1, "Профессия 2", 25)
            3L -> Profession(3, "Профессия 3", 25)
            4L -> Profession(1, "Профессия 4", 25)
            5L -> Profession(5, "Профессия 5", 25)
            6L -> Profession(5, "Профессия 6", 25)
            7L -> Profession(7, "Профессия 7", 25)
            8L -> Profession(7, "Профессия 8", 25)
            else -> Profession(0, "Тестовая профессия", 25)
        }
    }
}