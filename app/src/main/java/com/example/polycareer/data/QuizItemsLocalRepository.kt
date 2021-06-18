package com.example.polycareer.data

import com.example.polycareer.data.database.QuizDao
import com.example.polycareer.data.database.model.AnswersEntity
import com.example.polycareer.data.database.model.UsersAnswersEntity
import com.example.polycareer.domain.model.AnswersResponse
import com.example.polycareer.domain.model.QuestionsResponse
import com.example.polycareer.domain.model.Result

class QuizItemsLocalRepository(
    private val quizDao: QuizDao
) {

    private var currentTryNumber = 0L

    suspend fun getAllQuestions(): QuestionsResponse = try {
        val questions = quizDao.getAllQuestions()
        val result = mutableListOf<MutableList<String>>()
        for (question in questions) {
            if (result.size - 1 < question.questionId) {
                result.add(mutableListOf())
            }
            result[question.questionId].add(question.text)
        }
        QuestionsResponse(Result.DataCorrect, result)
    } catch (e: Exception) {
        QuestionsResponse(Result.Error("Database error"), null)
    }

    suspend fun setQuestions(answers: List<List<String>>): Boolean = try {
        quizDao.deleteAllQuestions()
        val result = mutableListOf<AnswersEntity>()
        for (answer in answers) {
            val questionId = answers.indexOf(answer)
            for (text in answer) {
                result.add(AnswersEntity(questionId = questionId, answerIndex = answer.indexOf(text), text = text))
            }
        }
        quizDao.setAllQuestions(result)
        true
    } catch (e: Exception) {
        false
    }

    suspend fun saveUserAnswer(questionId: Long, answerIndex: Long, userId: Long): Boolean = try {
        if (questionId == 0L) {
            currentTryNumber = quizDao.getCountOfUsersAttempts(userId) + 1
        }
        val answerId = quizDao.getAnswerIdByQuestionIdAndAnswerIndex(
            questionId = questionId,
            answerIndex = answerIndex
        )

        quizDao.insertNewAnswer(UsersAnswersEntity(
            userId = userId,
            answerId = answerId,
            tryNumber = currentTryNumber
        ))
        true
    } catch (e: Exception) {
        false
    }

    suspend fun clearUsersLastAttemptAnswers(userId: Long): Boolean = try {
        quizDao.deleteAnswersByUsersTryNumber(
            userId = userId,
            tryNumber = currentTryNumber
        )
        true
    } catch (e: Exception) {
        false
    }

    suspend fun getUsersLastAttemptAnswers(userId: Long): AnswersResponse = try {
        val currentTryNumber = quizDao.getCountOfUsersAttempts(userId) + 1

        val answers = quizDao.getAnswersByUsersTryNumber(userId = userId, tryNumber = currentTryNumber)
        val result = mutableListOf<Long>()
        answers.forEach { result.add(it.answerId) }
        AnswersResponse(Result.DataCorrect, result)
    } catch (e: Exception) {
        AnswersResponse(Result.Error("Database error"), null)
    }

}
