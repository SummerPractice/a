package com.example.polycareer.data.repository

import com.example.polycareer.domain.model.UserResult
import com.example.polycareer.domain.repository.OldResultsRepository
import java.util.*

class ResultsRepositoryTest : OldResultsRepository {
    override suspend fun getResults(): List<UserResult> {
        return listOf(
            UserResult(1, Date()),
            UserResult(2, Date()),
            UserResult(3, Date())
        )
    }
}