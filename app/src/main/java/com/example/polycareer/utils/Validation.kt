package com.example.polycareer.utils

import android.util.Patterns

fun isValidExamGrade(text: String): Boolean =
    text.isEmpty() || text.toInt() in 1..100

fun isValidIdGrade(text: String): Boolean =
    text.isEmpty() || text.toInt() <= 10

fun String.isValidName(): Boolean = this.isNotEmpty() && this.all { it.isLetter() }

fun String.isValidEmail(): Boolean =
    this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
