package com.example.polycareer.data

import com.example.polycareer.domain.model.ProfessionInfo
import com.example.polycareer.domain.repository.ProfessionRepository

class ProfessionsRepositoryImpl : ProfessionRepository {
    private val professions = listOf(
        ProfessionInfo(1, "Разработка и тестирование сложных программных продуктов", 5),
        ProfessionInfo(1, "Разработка мобильного и встраиваемого программного обеспечения", 3),
        ProfessionInfo(3, "Компьютерные науки, математическая кибернетика и системный анализ", 4),
        ProfessionInfo(1, "Искусственный интеллект и машинное обучение", 4),
        ProfessionInfo(5, "Проектирование и разработка цифровых систем и устройств", 2),
        ProfessionInfo(5, "Промышленный интернет вещей", 2),
        ProfessionInfo(7, "Инноватика и управление качеством", 3),
        ProfessionInfo(8, "Big Data & Data Science ", 4),
    )

    override suspend fun getProfession(id: Long): ProfessionInfo =
        professions.firstOrNull { it.id == id }
            ?: ProfessionInfo(0, "Тестовая профессия", 25)
}