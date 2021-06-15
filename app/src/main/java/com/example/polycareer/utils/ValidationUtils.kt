package com.example.polycareer.utils

import android.util.Patterns

fun String.isValidExamGrade(): Boolean =
    this.isNotEmpty() && this.toInt() in 1..100

fun String.isValidIdGrade(): Boolean =
    this.isNotEmpty() && this.toInt() in 0..10

fun String.isValidName(): Boolean = this.isNotEmpty() && this.all { it.isLetter() }

fun String.isValidEmail(): Boolean =
    this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
