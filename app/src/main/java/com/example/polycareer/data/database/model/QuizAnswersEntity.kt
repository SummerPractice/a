package com.example.polycareer.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "answers")
data class QuizAnswersEntity( // TODO()
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "users_choice") val usersChoice: Int
) {
    companion object {
        operator fun invoke(choice: Int): QuizAnswersEntity {
            return QuizAnswersEntity(
                usersChoice = choice
            )
        }
    }
}
