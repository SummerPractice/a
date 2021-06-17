package com.example.polycareer.data

import com.example.polycareer.domain.model.QuestionsResponse
import com.example.polycareer.domain.model.Result

class QuizItemsRemoteRepository {
    fun getQuestions(): QuestionsResponse {
        return QuestionsResponse(Result.Error("Lost connection to the server"), null)
    }
}
