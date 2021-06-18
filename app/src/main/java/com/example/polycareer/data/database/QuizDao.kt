package com.example.polycareer.data.database

import androidx.room.*
import com.example.polycareer.data.database.model.UsersAnswersEntity
import com.example.polycareer.data.database.model.AnswersEntity

@Dao
interface QuizDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setAllQuestions(questions: List<AnswersEntity>)

    @Query("DELETE FROM answers")
    suspend fun deleteAllQuestions()

    @Query("SELECT * FROM answers ORDER BY question_id, answer_index")
    suspend fun getAllQuestions(): List<AnswersEntity>

    @Insert
    suspend fun insertNewAnswer(answerId: UsersAnswersEntity)

    @Query("DELETE FROM users_answers")
    suspend fun deleteAllAnswers()

    @Query("DELETE FROM users_answers WHERE try_number =:tryNumber AND user_id = :userId")
    suspend fun deleteAnswersByUsersTryNumber(tryNumber: Long, userId: Long)
    
    @Query("SELECT COALESCE(MAX(try_number), 0) FROM users_answers WHERE user_id = :userId")
    suspend fun getCountOfUsersAttempts(userId: Long): Long

    @Query("SELECT * FROM users_answers WHERE try_number =:tryNumber AND user_id = :userId")
    suspend fun getAnswersByUsersTryNumber(tryNumber: Long, userId: Long): List<UsersAnswersEntity>

    @Query("SELECT id FROM answers WHERE question_id = :questionId AND answer_index = :answerIndex")
    suspend fun getAnswerIdByQuestionIdAndAnswerIndex(questionId: Long, answerIndex: Long): Long
}