package com.example.polycareer.domain.usecase

import com.example.polycareer.data.QuizItemsLocalRepository
import com.example.polycareer.domain.model.QuestionsResponse
import com.example.polycareer.data.QuizItemsRemoteRepository
import com.example.polycareer.domain.model.AnswersResponse
import com.example.polycareer.domain.model.Result
import com.example.polycareer.utils.getCurrentUserId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class QuizItemUseCase(
    private val localRepository: QuizItemsLocalRepository,
    private val remoteRepository: QuizItemsRemoteRepository
) {
    suspend fun saveUserAnswer(questionId: Int, answerId: Int): Result =
        withContext(Dispatchers.IO) {
            if (!localRepository.saveUserAnswer(
                    questionId = questionId.toLong(),
                    answerIndex = answerId.toLong(),
                    userId = getCurrentUserId())
            ) {
                 Result.Error("Failed to save data")
            }
            Result.DataCorrect
        }


    suspend fun getQuestions(): QuestionsResponse =
        withContext(Dispatchers.IO) {
            val localRepositoryData = localRepository.getAllQuestions()
            if (localRepositoryData.result is Result.DataCorrect) {
                localRepositoryData
            } else {
                val remoteRepositoryData = remoteRepository.getQuestions()
                if (remoteRepositoryData.result is Result.DataCorrect) {
                    remoteRepositoryData.questions?.let { localRepository.setQuestions(it) }
                }
                remoteRepositoryData
            }
        }


    suspend fun clearUserAnswers(): Result =
        withContext(Dispatchers.IO) {
            if (!localRepository.clearUsersLastAttemptAnswers(getCurrentUserId())) {
                Result.Error("Failed to clear data")
            }
            Result.DataCorrect
        }


    suspend fun getUserAnswers(): AnswersResponse =
        withContext(Dispatchers.IO) {
            localRepository.getUsersLastAttemptAnswers(getCurrentUserId())
        }
}