package com.example.polycareer.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "users_answers",
    primaryKeys = ["user_id", "answer_id", "try_number"],
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["id"],
        childColumns = ["user_id"]
    ),
    ForeignKey(
        entity = AnswersEntity::class,
        parentColumns = ["id"],
        childColumns = ["answer_id"]
    )]
)
data class UsersAnswersEntity(
    @ColumnInfo(name = "user_id") val userId: Long,
    @ColumnInfo(name = "answer_id") val answerId: Long,
    @ColumnInfo(name = "try_number") val tryNumber: Int = 0
)
