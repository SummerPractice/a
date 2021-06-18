package com.example.polycareer.domain.usecase

import com.example.polycareer.data.QuizItemsLocalRepository
import com.example.polycareer.domain.model.QuestionsResponse
import com.example.polycareer.data.QuizItemsRemoteRepository
import com.example.polycareer.domain.model.AnswersResponse
import com.example.polycareer.domain.model.Result
import com.example.polycareer.utils.getCurrentUserId

class QuizItemUseCase(
    private val localRepository: QuizItemsLocalRepository,
    private val remoteRepository: QuizItemsRemoteRepository
) {
    suspend fun saveUserAnswer(questionId: Int, answerId: Int): Result {
        if (!localRepository.saveUserAnswer(
                questionId = questionId.toLong(),
                answerIndex = answerId.toLong(),
                userId = getCurrentUserId())
        ) {
            return Result.Error("Failed to save data")
        }
        return Result.DataCorrect
    }

    suspend fun getQuestions(): QuestionsResponse {
        val localRepositoryData = localRepository.getAllQuestions()
        return if (localRepositoryData.result is Result.DataCorrect) {
            localRepositoryData
        } else {
            val remoteRepositoryData = remoteRepository.getQuestions()
            if (remoteRepositoryData.result is Result.DataCorrect) {
                remoteRepositoryData.questions?.let { localRepository.setQuestions(it) }
            }
            remoteRepositoryData
        }
    }

    suspend fun clearUserAnswers(): Result {
        if (!localRepository.clearUsersLastAttemptAnswers(getCurrentUserId())) {
            return Result.Error("Failed to clear data")
        }
        return Result.DataCorrect
    }

    suspend fun getUserAnswers(): AnswersResponse =
        localRepository.getUsersLastAttemptAnswers(getCurrentUserId())

}