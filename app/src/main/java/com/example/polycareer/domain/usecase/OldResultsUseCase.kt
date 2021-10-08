package com.example.polycareer.domain.usecase

import com.example.polycareer.domain.model.UserResultInfo
import com.example.polycareer.domain.repository.ResultsInfoRepository

class OldResultsUseCase(
    private val resultsRepository: ResultsInfoRepository
) {
    suspend fun getData(): Result {
        return try {
            Result.Success(resultsRepository.getResults())
        } catch (e: Exception) {
            Result.Error(e.message ?: "")
        }
    }

    sealed interface Result {
        class Success(
            val oldResults: List<UserResultInfo>,
        ) : Result

        class Error(val message: String) : Result
    }
}