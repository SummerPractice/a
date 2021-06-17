package com.example.polycareer.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.polycareer.data.database.model.*

@Database(entities = [UserDetailsEntity::class, UserGradesEntity::class,
    QuizQuestionsEntity::class, QuizAnswersEntity::class], version = 1)
@TypeConverters(AnswersConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val quizDao: QuizDao
}
