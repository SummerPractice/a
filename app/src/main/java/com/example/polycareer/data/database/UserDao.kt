package com.example.polycareer.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.polycareer.data.database.model.UserDetailsEntity
import com.example.polycareer.data.database.model.UserGradesEntity

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: UserDetailsEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGrades(grades: UserGradesEntity)

    @Query("SELECT * FROM user where id = :userId")
    suspend fun getDetailsById(userId: Long): UserDetailsEntity

    @Query("SELECT id FROM user where email = :email")
    suspend fun getIdByEmail(email: String): List<Long>

    @Query("SELECT * FROM grades WHERE user_id = :userId")
    suspend fun getGradesByUserId(userId: Long): UserGradesEntity
}