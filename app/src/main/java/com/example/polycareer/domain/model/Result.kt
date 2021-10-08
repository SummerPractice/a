package com.example.polycareer.domain.model

interface Result {
    object DataCorrect : Result
    object WrongData : Result
    class Error(val message: String) : Result
}