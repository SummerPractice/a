package com.example.polycareer.utils

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.polycareer.App

@Suppress("detekt.UnsafeCast")
fun <T> MutableLiveData<T>.asLiveData() = this as LiveData<T>

fun getCurrentUserId(): Long {
    val preferences =
        App.applicationContext().getSharedPreferences(App.CURRENT_USER_ID, Context.MODE_PRIVATE)
    return preferences.getLong(App.USER_ID_KEY, App.USER_ID_DEFAULT_VALUE)
}

fun setCurrentUser(userId: Long) {
    val preferences = App.applicationContext().getSharedPreferences(App.CURRENT_USER_ID, Context.MODE_PRIVATE)
    with (preferences.edit()) {
        putLong(App.USER_ID_KEY, userId)
        apply()
    }
}