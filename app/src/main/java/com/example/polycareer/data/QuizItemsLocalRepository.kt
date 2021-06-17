package com.example.polycareer.data

import com.example.polycareer.data.database.QuizDao
import com.example.polycareer.data.database.model.QuizAnswersEntity
import com.example.polycareer.data.database.model.QuizQuestionsEntity
import com.example.polycareer.domain.model.AnswersResponse
import com.example.polycareer.domain.model.QuestionsResponse
import com.example.polycareer.domain.model.Result

class QuizItemsLocalRepository(
    private val quizDao: QuizDao
) {
    suspend fun getAllQuestions(): QuestionsResponse = try {
        val questions = quizDao.getAllQuestions()
        val result = mutableListOf<List<String>>()
        questions.forEach { result.add(it.answers) }
        QuestionsResponse(Result.DataCorrect, result)
    } catch (e: Exception) {
        QuestionsResponse(Result.Error("Database error"), null)
    }

    suspend fun setQuestions(answers: List<List<String>>): Boolean = try {
        val resultEntity = mutableListOf<QuizQuestionsEntity>()
        answers.forEach { resultEntity.add(QuizQuestionsEntity(answers = it)) }
        quizDao.setAllQuestions(resultEntity)
        true
    } catch (e: Exception) {
        false
    }

    suspend fun saveUserAnswer(answerId: Int): Boolean = try {
        quizDao.insertNewAnswer(QuizAnswersEntity(choice = answerId))
        true
    } catch (e: Exception) {
        false
    }

    suspend fun clearUserAnswers(): Boolean = try {
        quizDao.deleteAllAnswers()
        true
    } catch (e: Exception) {
        false
    }

    suspend fun getAllAnswers(): AnswersResponse = try {
        val answers = quizDao.getAllAnswers()
        val result = mutableListOf<Int>()
        answers.forEach { result.add(it.usersChoice) }
        AnswersResponse(Result.DataCorrect, result)
    } catch (e: Exception) {
        AnswersResponse(Result.Error("Database error"), null)
    }

}
