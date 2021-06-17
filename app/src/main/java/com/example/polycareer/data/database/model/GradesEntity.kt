package com.example.polycareer.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.polycareer.domain.model.UserGrades

@Entity(
    tableName = "grades",
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["id"],
        childColumns = ["user_id"]
    )]
)
data class GradesEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "user_id") val userId: Long = 0,
    val math: Int,
    val rus: Int,
    val phys: Int,
    val inf: Int,
    val individual: Int
) {
    companion object {
        operator fun invoke(userId: Long, grades: UserGrades): GradesEntity {
            return GradesEntity(
                userId,
                grades.mathematics.toInt(),
                grades.russian.toInt(),
                grades.physics.toInt(),
                grades.informatics.toInt(),
                if (grades.individualAchievements.isEmpty()) 0 else grades.individualAchievements.toInt()
            )
        }
    }
}
