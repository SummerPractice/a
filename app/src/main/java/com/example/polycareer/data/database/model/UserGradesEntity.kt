package com.example.polycareer.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "grades",
    foreignKeys = [ForeignKey(
        entity = UserDetailsEntity::class,
        parentColumns = ["id"],
        childColumns = ["user_id"]
    )]
)
data class UserGradesEntity(
    @PrimaryKey @ColumnInfo(name = "user_id") val userId: Long = 0,
    val math: Int,
    val rus: Int,
    val phys: Int,
    val inf: Int,
    val individual: Int
)
