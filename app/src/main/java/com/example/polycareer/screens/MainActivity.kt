package com.example.polycareer.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.polycareer.App
import com.example.polycareer.R
import com.example.polycareer.data.database.dao.UserDao
import com.example.polycareer.data.database.model.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.getKoin
import org.koin.core.context.GlobalContext.get

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_PolyCareer)
        setContentView(R.layout.activity_main)
        createDefaultUser()
    }

    private fun createDefaultUser() = runBlocking {
        launch(Dispatchers.IO) {
            val userDao: UserDao = getKoin().get()
            val userEntity = UserEntity(id = App.USER_ID_DEFAULT_VALUE)
            userDao.insertUser(userEntity)
        }
    }
}