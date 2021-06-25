package com.example.polycareer.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.polycareer.data.database.model.DirectionEntity

@Dao
interface DirectionsDao {
    @Query("SELECT * FROM directions")
    suspend fun getAllDirections(): List<DirectionEntity>

    @Query("DELETE FROM directions")
    suspend fun deleteAllDirections()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setAllDirections(directions: List<DirectionEntity>)
}
