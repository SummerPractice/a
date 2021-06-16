package com.example.polycareer.data

import com.example.polycareer.domain.model.QuestionsResponse
import com.example.polycareer.domain.repository.QuizItemsRepository
import com.example.polycareer.domain.model.Result

class QuizItemsRepositoryImpl : QuizItemsRepository {

    private val userAnswers = mutableListOf<Int>()

    private val questions = listOf(
        listOf("kotlin", "java"),
        listOf("android"),
        listOf("месси", "роналду", "неймар"),
    )

    override fun getQuestions(): QuestionsResponse {
        return QuestionsResponse(Result.DataCorrect, questions)
    }

    override fun saveUserAnswer(answerId: Int): Boolean {
        userAnswers.add(answerId)
        return true;
    }
}
