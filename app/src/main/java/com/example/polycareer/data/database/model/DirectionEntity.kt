package com.example.polycareer.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "directions")
data class DirectionEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val description: String,
    val link: String
)