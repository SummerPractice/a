package com.example.polycareer.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.polycareer.data.database.model.AnswersEntity
import com.example.polycareer.data.database.model.CoeffsEntity
import com.example.polycareer.data.database.model.ResultInfo
import com.example.polycareer.data.database.model.UsersAnswersEntity

@Dao
interface QuizDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setAllQuestions(questions: List<AnswersEntity>)

    @Query("DELETE FROM answers")
    suspend fun deleteAllQuestions()

    @Query("DELETE FROM users_answers WHERE try_number IN (" +
            "SELECT try_number FROM users_answers GROUP BY try_number HAVING count(*) < (" +
            "SELECT max(question_id) + 1 FROM answers))")
    suspend fun deleteUnfinishedTests()

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

    @Query("SELECT id FROM answers WHERE question_id = :questionId AND answer_index = :answerIndex")
    suspend fun getAnswerIdByQuestionIdAndAnswerIndex(questionId: Long, answerIndex: Long): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setAllCoeffs(coeffs: List<CoeffsEntity>)

    @Query("DELETE FROM coeffs")
    suspend fun deleteAllCoeffs()

    @Query("SELECT * FROM coeffs WHERE answerId in (:answerIds)")
    suspend fun getAnswerData(answerIds: List<Long>): List<CoeffsEntity>

    @Query("SELECT * FROM users_answers WHERE user_id = :userId AND try_number = :tryNumber")
    suspend fun getUserAnswers(userId: Long, tryNumber: Long): List<UsersAnswersEntity>

    @Query("SELECT try_number, time FROM users_answers GROUP BY try_number HAVING max(time)")
    suspend fun getTries(): List<ResultInfo>
}