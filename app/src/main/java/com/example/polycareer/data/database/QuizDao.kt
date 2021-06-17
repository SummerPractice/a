package com.example.polycareer.data.database

import androidx.room.*
import com.example.polycareer.data.database.model.UsersAnswersEntity
import com.example.polycareer.data.database.model.AnswersEntity

@Dao
interface QuizDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setAllQuestions(questions: List<AnswersEntity>)

    @Query("SELECT * FROM answers ORDER BY question_id, answer_index")
    suspend fun getAllQuestions(): List<AnswersEntity>

    @Insert
    suspend fun insertNewAnswer(answerId: UsersAnswersEntity)

    @Query("DELETE FROM users_answers")
    suspend fun deleteAllAnswers()

    @Query("DELETE FROM users_answers WHERE try_number =:tryNumber AND user_id = :userId")
    suspend fun deleteAnswersByUsersTryNumber(tryNumber: Int, userId: Long)

    @Query("SELECT * FROM users_answers")
    suspend fun getAllAnswers(): List<UsersAnswersEntity>
}