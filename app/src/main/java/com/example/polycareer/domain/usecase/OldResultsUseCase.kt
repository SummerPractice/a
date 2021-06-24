package com.example.polycareer.domain.usecase

import com.example.polycareer.domain.model.UserResult
import com.example.polycareer.domain.repository.OldResultsRepository

class OldResultsUseCase(
    private val resultsRepository: OldResultsRepository
) {
    suspend fun getData(): Result {
        return Result.Success(resultsRepository.getResults())
    }

    sealed interface Result {
        class Success(
            val oldResults: List<UserResult>,
        ) : Result

        class Error(val message: String) : Result
    }
}