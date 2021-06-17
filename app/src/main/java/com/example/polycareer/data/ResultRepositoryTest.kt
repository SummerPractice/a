package com.example.polycareer.data

import com.example.polycareer.domain.model.UserAnswer
import com.example.polycareer.domain.repository.ResultsRepository

class ResultRepositoryTest : ResultsRepository {
    override suspend fun getAnswers(): List<UserAnswer> {
        return listOf(
            UserAnswer(1, mapOf(
                "fi" to 0.08,
                "pi1" to 0.08,
                "pi2" to 0.2,
                "ivt" to 0.12,
            )),

            UserAnswer(3, mapOf(
                "fi" to 0.15,
                "mot" to 0.25,
                "ic" to 0.15,
                "cic" to 0.15,
            )),

            UserAnswer(7, mapOf(
                "yk" to 0.5,
                "inn" to 0.5,
            )),

            UserAnswer(5, mapOf(
                "ivt" to 0.3,
            )),
        )
    }
}
