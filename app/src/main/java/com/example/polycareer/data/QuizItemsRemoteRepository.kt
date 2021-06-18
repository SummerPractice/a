package com.example.polycareer.data

import com.example.polycareer.domain.model.QuestionsResponse
import com.example.polycareer.domain.model.Result

class QuizItemsRemoteRepository {
    fun getQuestions(): QuestionsResponse {
        return QuestionsResponse(Result.DataCorrect,
            listOf(
                listOf("Печенье", "Пряники", "Вафли"),
                listOf("Матобес", "Пи"),
                listOf("Sprite", "7UP")
        ))
    }
}
