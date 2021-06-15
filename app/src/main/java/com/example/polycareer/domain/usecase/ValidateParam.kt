package com.example.polycareer.domain.usecase

open class ValidateParam {
    protected suspend fun validate(condition: Boolean): Result =
        if (condition) Result.DataCorrect
        else Result.WrongData

    sealed interface Result {
        object DataCorrect : Result
        object WrongData : Result
        class Error(val message: String) : Result
    }
}