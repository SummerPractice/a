package com.example.polycareer.data

import com.example.polycareer.data.database.QuizDao
import com.example.polycareer.data.database.model.UsersAnswersEntity
import com.example.polycareer.data.database.model.AnswersEntity
import com.example.polycareer.domain.model.AnswersResponse
import com.example.polycareer.domain.model.QuestionsResponse
import com.example.polycareer.domain.model.Result

class QuizItemsLocalRepository(
    private val quizDao: QuizDao
) {
    suspend fun getAllQuestions(): QuestionsResponse = try {
        val questions = quizDao.getAllQuestions()
        val result = mutableListOf<MutableList<String>>()
        for (question in questions) {
            if (result.size < question.questionId) {
                result.add(mutableListOf())
            }
            result[question.questionId].add(question.text)
        }
        QuestionsResponse(Result.DataCorrect, result)
    } catch (e: Exception) {
        QuestionsResponse(Result.Error("Database error"), null)
    }

    suspend fun setQuestions(answers: List<List<String>>): Boolean = try {
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

    suspend fun saveUserAnswer(answerId: Int): Boolean = try { // TODO()
//        quizDao.insertNewAnswer(UsersAnswersEntity())
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
        val result = mutableListOf<Long>()
        answers.forEach { result.add(it.answerId) }
        AnswersResponse(Result.DataCorrect, result)
    } catch (e: Exception) {
        AnswersResponse(Result.Error("Database error"), null)
    }

}
