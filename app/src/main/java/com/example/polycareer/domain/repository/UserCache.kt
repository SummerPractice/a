package com.example.polycareer.domain.repository

interface UserCache {
    fun getCurrentUserId(): Long

    fun setCurrentUser(userId: Long)
}