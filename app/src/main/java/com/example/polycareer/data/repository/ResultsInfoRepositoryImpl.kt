package com.example.polycareer.data.repository

import com.example.polycareer.data.database.dao.QuizDao
import com.example.polycareer.domain.model.UserResultInfo
import com.example.polycareer.domain.repository.ResultsInfoRepository
import java.util.*

class ResultsInfoRepositoryImpl(
    private val dao: QuizDao
) : ResultsInfoRepository {

    override suspend fun getResults(): List<UserResultInfo> {
        return dao.getTries().map { UserResultInfo(it) }
    }
}