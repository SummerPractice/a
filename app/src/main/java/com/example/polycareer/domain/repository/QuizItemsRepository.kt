package com.example.polycareer.domain.repository

import com.example.polycareer.domain.model.QuestionsResponse

interface QuizItemsRepository {
    fun getQuestions(): QuestionsResponse

    fun saveUserAnswer(answerId: Int): Boolean
}