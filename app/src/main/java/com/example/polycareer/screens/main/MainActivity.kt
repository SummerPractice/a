package com.example.polycareer.screens.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.polycareer.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_PolyCareer)
        setContentView(R.layout.activity_main)
    }
}