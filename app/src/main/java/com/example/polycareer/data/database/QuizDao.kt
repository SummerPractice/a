package com.example.polycareer.data.database

import androidx.room.*
import com.example.polycareer.data.database.model.AnswersConverter
import com.example.polycareer.data.database.model.QuizAnswersEntity
import com.example.polycareer.data.database.model.QuizQuestionsEntity

@Dao
interface QuizDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setAllQuestions(questions: List<QuizQuestionsEntity>)

    @TypeConverters(AnswersConverter::class)
    @Query("SELECT * FROM questions")
    suspend fun getAllQuestions(): List<QuizQuestionsEntity>

    @Insert
    suspend fun insertNewAnswer(answerId: QuizAnswersEntity)

    @Query("DELETE FROM answers")
    suspend fun deleteAllAnswers()

    @Query("SELECT * FROM answers")
    suspend fun getAllAnswers(): List<QuizAnswersEntity>
}