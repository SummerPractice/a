package com.example.polycareer.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class QuizQuestionsEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val answers: List<String>
) {
    companion object {
        operator fun invoke(answers: List<String>): QuizQuestionsEntity {
            return QuizQuestionsEntity(
                answers = answers
            )
        }
    }
}
