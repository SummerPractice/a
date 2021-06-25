package com.example.polycareer.data.repository

import com.example.polycareer.domain.model.DirectionInfo
import com.example.polycareer.exception.DatabaseException
import com.example.polycareer.exception.LostConnectionException
import com.example.polycareer.exception.WrongResponseException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DirectionsUseCase(
    private val localRepository: DirectionsLocalRepository,
    private val remoteRepository: DirectionsRemoteRepository,
) {

    private var directionsList: List<DirectionInfo>? = null

    suspend fun getDirection(id: Long): DirectionInfo {
        if (directionsList == null) {
            getAllDirections()
        }
        return directionsList!!.first { it.id == id }
    }

    private suspend fun getAllDirections() = withContext(Dispatchers.IO) {
        try {
            directionsList = localRepository.getAllDirections()
        } catch (e: DatabaseException) {
            try {
                val remoteRepositoryData = remoteRepository.getAllDirections()
                directionsList = remoteRepositoryData.directions
                if (!localRepository.setAllDirections(remoteRepositoryData))
                    throw DatabaseException()
            } catch (e: LostConnectionException) {
                throw LostConnectionException()
            } catch (e: WrongResponseException) {
                throw WrongResponseException(e.message)
            }
        }
    }

}