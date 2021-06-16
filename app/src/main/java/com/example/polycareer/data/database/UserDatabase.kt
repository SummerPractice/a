package com.example.polycareer.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.polycareer.data.database.model.UserDetailsEntity
import com.example.polycareer.data.database.model.UserGradesEntity

@Database(entities = [UserDetailsEntity::class, UserGradesEntity::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract val userDao: UserDao
}
