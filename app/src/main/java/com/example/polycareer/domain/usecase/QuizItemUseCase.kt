package com.example.polycareer.domain.usecase

import com.example.polycareer.data.repository.QuizItemsLocalRepository
import com.example.polycareer.domain.model.QuestionsResponse
import com.example.polycareer.data.repository.QuizItemsRemoteRepository
import com.example.polycareer.domain.model.Result
import com.example.polycareer.domain.repository.UserCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class QuizItemUseCase(
    private val localRepository: QuizItemsLocalRepository,
    private val remoteRepository: QuizItemsRemoteRepository,
    private val userCache: UserCache
) {
    suspend fun saveUserAnswer(questionId: Int, answerId: Int): Result =
        withContext(Dispatchers.IO) {
            if (!localRepository.saveUserAnswer(
                    questionId = questionId.toLong(),
                    answerIndex = answerId.toLong(),
                    userId = userCache.getCurrentUserId()
                )
            ) {
                Result.Error("Failed to save data")
            }
            Result.DataCorrect
        }

    suspend fun getQuestions(): QuestionsResponse = withContext(Dispatchers.IO) {
        val localRepositoryData = localRepository.getAllQuestions()
        if (localRepositoryData.result is Result.DataCorrect) {
            localRepositoryData
        } else {
            val remoteRepositoryData = remoteRepository.getQuestions()
            if (remoteRepositoryData.result is Result.DataCorrect) {
                remoteRepositoryData.let { localRepository.setQuestions(it) }
                val result = mutableListOf<MutableList<String>>()
                val source = remoteRepositoryData.quiz!!
                for (list in source) {
                    if (result.size - 1 < source.indexOf(list)) {
                        result.add(mutableListOf())
                    }
                    for (entry in list) {
                        result[source.indexOf(list)].add(entry.value)
                    }
                }
                QuestionsResponse(Result.DataCorrect, result)
            } else {
                QuestionsResponse(Result.Error("Lost connection to the server"), null)
            }
        }
    }

    suspend fun clearUserAnswers(): Result = withContext(Dispatchers.IO) {
        if (!localRepository.clearUsersLastAttemptAnswers(userCache.getCurrentUserId())) {
            Result.Error("Failed to clear data")
        }
        Result.DataCorrect
    }

    fun getTryNumber(): Long {
        return localRepository.currentTryNumber
    }
}