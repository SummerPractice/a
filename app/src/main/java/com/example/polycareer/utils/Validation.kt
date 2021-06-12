package com.example.polycareer.utils

import android.text.TextUtils
import android.util.Patterns

fun isValidExamGrade(text: String): Boolean =
    text.isEmpty() || text.toInt() in 1..100

fun isValidIdGrade(text: String): Boolean =
    text.isEmpty() || text.toInt() <= 10

fun isValidEmail(text: String): Boolean =
    if (TextUtils.isEmpty(text)) {
        false
    } else {
        Patterns.EMAIL_ADDRESS.matcher(text).matches()
    }

fun isValidName(text: String): Boolean =
    text.isNotEmpty() && text.all { it.isLetter() }