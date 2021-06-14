package com.example.polycareer.domain.usecase

interface ValidateParam {
    sealed interface Result {
        object DataCorrect : Result
        object WrongData : Result
        class Error(val message: String) : Result
    }
}