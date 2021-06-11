package com.example.polycareer.utils

fun isValidExamGrade(text: String): Boolean =
    text.isEmpty() || text.toInt() in 1..100

fun isValidIdGrade(text: String): Boolean =
    text.isEmpty() || text.toInt() <= 10
