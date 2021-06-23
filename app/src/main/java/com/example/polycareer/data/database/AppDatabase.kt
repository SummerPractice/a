package com.example.polycareer.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.polycareer.data.database.dao.QuizDao
import com.example.polycareer.data.database.dao.UserDao
import com.example.polycareer.data.database.model.*

@Database(entities = [UserEntity::class, GradesEntity::class,
    AnswersEntity::class, UsersAnswersEntity::class, CoeffsEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val quizDao: QuizDao
}
