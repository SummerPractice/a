package com.example.polycareer.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.polycareer.data.database.model.UserEntity
import com.example.polycareer.data.database.model.GradesEntity

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: UserEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGrades(grades: GradesEntity)

    @Query("SELECT * FROM user where id = :userId")
    suspend fun getDetailsById(userId: Long): UserEntity

    @Query("SELECT id FROM user where email = :email")
    suspend fun getIdByEmail(email: String): List<Long>

    @Query("SELECT * FROM grades WHERE user_id = :userId")
    suspend fun getGradesByUserId(userId: Long): GradesEntity
}