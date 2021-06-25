package com.example.polycareer.data.repository

import com.example.polycareer.data.database.dao.DirectionsDao
import com.example.polycareer.data.database.dao.ProfessionsDao
import com.example.polycareer.data.database.model.DirectionEntity
import com.example.polycareer.data.database.model.ProfessionEntity
import com.example.polycareer.domain.model.DirectionInfo
import com.example.polycareer.domain.model.DirectionsApiResponse
import com.example.polycareer.domain.model.ProfessionInfo
import com.example.polycareer.exception.DatabaseException

class ProfessionsLocalRepository(
    private val professionsDao: ProfessionsDao
) {
    suspend fun getAllProfessions(): List<ProfessionInfo> {
        val resultEntities = professionsDao.getAllProfessions()

        if (resultEntities.isNotEmpty()) {
            val result =  mutableListOf<ProfessionInfo>()
            for (entity in resultEntities) {
                result.add(ProfessionInfo(
                        id = entity.id,
                        name = entity.title))
            }
            return result
        } else {
            throw DatabaseException()
        }
    }

    suspend fun setAllProfessions(professions: List<ProfessionInfo>): Boolean = try {
        professionsDao.deleteAllProfessions()
        val resultProfessions = mutableListOf<ProfessionEntity>()
        for (profession in professions) {
            resultProfessions.add(
                ProfessionEntity(
                    id = profession.id,
                    title = profession.name
                )
            )
        }
        professionsDao.setAllProfessions(resultProfessions)
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}
