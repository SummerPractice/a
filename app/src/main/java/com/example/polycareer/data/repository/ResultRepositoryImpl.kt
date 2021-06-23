package com.example.polycareer.data.repository

import com.example.polycareer.data.database.dao.QuizDao
import com.example.polycareer.domain.model.UserAnswer
import com.example.polycareer.domain.repository.ResultsRepository

class ResultRepositoryImpl(
    private val quizDao: QuizDao
) : ResultsRepository{

    override suspend fun getAnswers(userId: Long, tryNumber: Long): List<UserAnswer> {
        val answers = quizDao.getUserAnswers(userId, tryNumber)
        val data = quizDao.getAnswerData(answers)
        return data.map { UserAnswer(it) }
    }
}