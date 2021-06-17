package com.example.polycareer.data.database.model

import androidx.room.TypeConverter

class AnswersConverter {
    companion object {
        @TypeConverter
        @JvmStatic
        fun fromAnswers(questions: List<String>): String = questions.joinToString(separator = "%%")

        @TypeConverter
        @JvmStatic
        fun toAnswers(data: String): List<String> = data.split(",")
    }
}