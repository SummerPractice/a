package com.example.polycareer.domain.usecase

import com.example.polycareer.data.repository.QuizItemsLocalRepository
import com.example.polycareer.domain.model.QuestionsResponse
import com.example.polycareer.data.repository.QuizItemsRemoteRepository
import com.example.polycareer.domain.repository.UserCache
import com.example.polycareer.exception.DatabaseException
import com.example.polycareer.exception.LostConnectionException
import com.example.polycareer.exception.WrongResponseException
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
            Result.Success
        }

    suspend fun getQuestions(): Result = withContext(Dispatchers.IO) {
        try {
            return@withContext Result.DataCorrect(localRepository.getAllQuestions())
        } catch (e: DatabaseException) {

            val remoteRepositoryData = try {
                remoteRepository.getQuestions()
            } catch (e: LostConnectionException) {
                return@withContext Result.Error("Lost connection with server")
            } catch (e: WrongResponseException) {
                return@withContext Result.Error(e.message)
            }

            if (!localRepository.setQuestions(remoteRepositoryData))
                return@withContext Result.Error("Database error")

            val result = mutableListOf<MutableList<String>>()
            val source = remoteRepositoryData.quiz
            for (list in source) {
                if (result.size - 1 < source.indexOf(list)) {
                    result.add(mutableListOf())
                }
                for (entry in list) {
                    result[source.indexOf(list)].add(entry.value)
                }
            }
            return@withContext Result.DataCorrect(QuestionsResponse(result))
        }
    }

    suspend fun clearUserAnswers(): Result = withContext(Dispatchers.IO) {
        val userId = userCache.getCurrentUserId()
        if (!localRepository.clearUsersLastAttemptAnswers(userId)) {
            Result.Error("Failed to clear data")
        }
        Result.Success
    }

    suspend fun getTryNumber(): Long = localRepository.currentTryNumber

    suspend fun clearUserLastAnswer() : Result = withContext(Dispatchers.IO) {
        val userId = userCache.getCurrentUserId()
        if (!localRepository.clearUsersLastAnswer(userId)) {
            Result.Error("Failed to clear data")
        }
        Result.Success
    }

    sealed interface Result {
        class DataCorrect(val questionsResponse: QuestionsResponse) : Result
        object Success : Result
        class Error(val message: String) : Result
    }
}