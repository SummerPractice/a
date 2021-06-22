package com.example.polycareer.data

import com.example.polycareer.domain.model.ProfessionInfo
import com.example.polycareer.domain.repository.ProfessionRepository

class ProfessionsRepositoryTest : ProfessionRepository {
    override suspend fun getProfession(id: Long): ProfessionInfo {
        return when(id) {
            1L -> ProfessionInfo(1, "Разработка и тестирование сложных программных продуктов", 5)
            2L -> ProfessionInfo(1, "Разработка мобильного и встраиваемого программного обеспечения", 3)
            3L -> ProfessionInfo(3, "Компьютерные науки, математическая кибернетика и системный анализ", 4)
            4L -> ProfessionInfo(1, "Искусственный интеллект и машинное обучение", 4)
            5L -> ProfessionInfo(5, "Проектирование и разработка цифровых систем и устройств", 2)
            6L -> ProfessionInfo(5, "Промышленный интернет вещей", 2)
            7L -> ProfessionInfo(7, "Инноватика и управление качеством", 3)
            8L -> ProfessionInfo(8, "Big Data & Data Science ", 4)
            else -> ProfessionInfo(0, "Тестовая профессия", 25)
        }
    }
}