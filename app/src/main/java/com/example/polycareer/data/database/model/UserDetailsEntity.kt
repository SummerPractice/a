package com.example.polycareer.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.polycareer.domain.model.UserDetails

@Entity(tableName = "user")
data class UserDetailsEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val firstname: String,
    val lastname: String,
    val email: String
) {
    companion object {
        operator fun invoke(user: UserDetails): UserDetailsEntity {
            return UserDetailsEntity(
                firstname = user.name,
                lastname = user.surname,
                email = user.email
            )
        }
    }
}