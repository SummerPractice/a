package com.example.polycareer.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "coeffs",
    foreignKeys = [ForeignKey(
        entity = AnswersEntity::class,
        parentColumns = ["id"],
        childColumns = ["answerId"]
    )])
data class CoeffsEntity(
    @PrimaryKey(autoGenerate = true) val answerId: Long = 0,
    val profession: Long = -1,
    val yk: Double = 0.0,
    val ytc: Double = 0.0,
    val inn: Double = 0.0,
    val ivt: Double = 0.0,
    val pi1: Double = 0.0,
    val pi2: Double = 0.0,
    val cic: Double = 0.0,
    val ic: Double = 0.0,
    val fi: Double = 0.0,
    val mot: Double = 0.0,
)
