package com.example.polycareer.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "answers")
data class AnswersEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "answer_index") val answerIndex: Int,
    @ColumnInfo(name = "question_id") val questionId: Int,
    @ColumnInfo val text: String,
) {
    companion object {
        operator fun invoke(questionId: Int, answerIndex: Int, text: String): AnswersEntity {
            return AnswersEntity(
                questionId = questionId,
                answerIndex = answerIndex,
                text = text
            )
        }
    }
}
