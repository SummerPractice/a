package com.example.polycareer.domain.usecase

import com.example.polycareer.domain.model.Result

open class ValidateParam {
    protected suspend fun validate(condition: Boolean): Result =
        if (condition) Result.DataCorrect
        else Result.WrongData
}