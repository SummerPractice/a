package com.example.polycareer.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.polycareer.domain.model.UserDetails

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val firstname: String = "",
    val lastname: String = "",
    val email: String = ""
) {
    companion object {
        operator fun invoke(user: UserDetails): UserEntity {
            return UserEntity(
                firstname = user.name,
                lastname = user.surname,
                email = user.email
            )
        }
    }
}