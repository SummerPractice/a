package com.example.polycareer.data

import com.example.polycareer.domain.model.Profession
import com.example.polycareer.domain.repository.ProfessionRepository

class ProfessionsRepositoryTest : ProfessionRepository {
    override suspend fun getProfession(id: Long): Profession {
        return when(id) {
            1L -> Profession(1, "Проектирование, разработка, тестирование и сопровождение сложных программных продуктов", 5)
            3L -> Profession(3, "Компьютерные науки, математическая кибернетика и системный анализ ", 4)
            5L -> Profession(5, "Проектирование и разработка цифровых систем и устройств ", 2)
            7L -> Profession(7, "Инноватика и управление качеством ", 3)
            else -> Profession(0, "", 0)
        }
    }
}