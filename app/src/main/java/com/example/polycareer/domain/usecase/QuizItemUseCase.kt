package com.example.polycareer.domain.usecase

import com.example.polycareer.domain.model.QuestionsResponse
import com.example.polycareer.domain.model.Result
import com.example.polycareer.domain.repository.QuizItemsRepository

class QuizItemUseCase(
    private val repository: QuizItemsRepository
) {
    suspend fun saveUserAnswer(answerId: Int): Result {
        if (!repository.saveUserAnswer(answerId)) {
            return Result.Error("Failed to save data")
        }
        return Result.DataCorrect
    }

    suspend fun getQuestions(): QuestionsResponse {
        return repository.getQuestions()
    }

}