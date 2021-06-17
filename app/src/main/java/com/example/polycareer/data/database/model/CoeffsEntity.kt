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
    val profession: Long,
    val yk: Double,
    val ytc: Double,
    val inn: Double,
    val pi1: Double,
    val pi2: Double,
    val cic: Double,
    val ic: Double,
    val fi: Double,
    val mot: Double,
) {
    companion object {
        operator fun invoke(answerId: Long,
                            yk: Double = 0.0,
                            ytc: Double = 0.0,
                            inn: Double = 0.0,
                            pi1: Double = 0.0,
                            pi2: Double = 0.0,
                            cic: Double = 0.0,
                            ic: Double = 0.0,
                            fi: Double = 0.0,
                            mot: Double = 0.0)
        : CoeffsEntity {
            return CoeffsEntity(
                answerId = answerId,
                yk = yk,
                ytc = ytc,
                inn = inn,
                pi1 = pi1,
                pi2 = pi2,
                cic = cic,
                ic = ic,
                fi = fi,
                mot = mot,
            )
        }
    }
}
