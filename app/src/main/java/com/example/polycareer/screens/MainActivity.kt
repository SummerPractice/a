package com.example.polycareer.screens

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.polycareer.App
import com.example.polycareer.R
import com.example.polycareer.data.database.dao.UserDao
import com.example.polycareer.data.database.model.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.getKoin

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_PolyCareer)
        super.onCreate(savedInstanceState)
        setMode()
        setContentView(R.layout.activity_main)
        createDefaultUser()
    }

    private fun setMode() {
        val appSharedPrefs: SharedPreferences = getSharedPreferences(App.IS_DARK_MODE, 0)
        if ((android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) && (!appSharedPrefs.contains(App.DARK_MODE_KEY))) {
            return
        }

        val isNightModeOn: Boolean = appSharedPrefs.getBoolean(App.DARK_MODE_KEY, false)
        if (isNightModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun createDefaultUser() = runBlocking {
        launch(Dispatchers.IO) {
            val userDao: UserDao = getKoin().get()
            val userEntity = UserEntity(id = App.USER_ID_DEFAULT_VALUE)
            userDao.insertUser(userEntity)
        }
    }
}