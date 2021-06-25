package com.example.polycareer.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "professions")
data class ProfessionEntity(
    @PrimaryKey val id: Long,
    val title: String,
)