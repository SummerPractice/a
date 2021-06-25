package com.example.polycareer.data.repository

import com.example.polycareer.data.database.dao.DirectionsDao
import com.example.polycareer.data.database.model.DirectionEntity
import com.example.polycareer.domain.model.DirectionInfo
import com.example.polycareer.domain.model.DirectionsApiResponse
import com.example.polycareer.exception.DatabaseException

class DirectionsLocalRepository(
    private val directionsDao: DirectionsDao
) {
    suspend fun getAllDirections(): List<DirectionInfo> {
        val resultEntities = directionsDao.getAllDirections()

        if (resultEntities.isNotEmpty()) {
            val result =  mutableListOf<DirectionInfo>()
            for (entity in resultEntities) {
                result.add(DirectionInfo(
                        id = entity.id,
                        name = entity.title,
                        description = entity.description,
                        url = entity.link))
            }
            return result
        } else {
            throw DatabaseException()
        }
    }

    suspend fun setAllDirections(directions: DirectionsApiResponse): Boolean = try {
        directionsDao.deleteAllDirections()
        val resultDirections = mutableListOf<DirectionEntity>()
        for (direction in directions.directions) {
            resultDirections.add(
                DirectionEntity(
                    id = direction.id,
                    title = direction.name,
                    description = direction.description,
                    link = direction.url
                )
            )
        }
        directionsDao.setAllDirections(resultDirections)
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}
