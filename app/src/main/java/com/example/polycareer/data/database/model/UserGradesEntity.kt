package com.example.polycareer.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.polycareer.domain.model.UserGrades

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
) {
    companion object {
        operator fun invoke(userId: Long, grades: UserGrades): UserGradesEntity {
            return UserGradesEntity(
                userId,
                grades.mathematics.toInt(),
                grades.russian.toInt(),
                grades.physics.toInt(),
                grades.informatics.toInt(),
                grades.individualAchievements.toInt()
            )
        }
    }
}
