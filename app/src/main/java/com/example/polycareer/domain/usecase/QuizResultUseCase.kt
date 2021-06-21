package com.example.polycareer.domain.usecase

import com.example.polycareer.domain.model.Direction
import com.example.polycareer.domain.model.Profession
import com.example.polycareer.domain.model.UserAnswer
import com.example.polycareer.domain.repository.ProfessionRepository
import com.example.polycareer.domain.repository.ResultsRepository
import com.example.polycareer.utils.getCurrentUserId

class QuizResultUseCase(
    private val resultsRepository: ResultsRepository,
    private val professionsRepository: ProfessionRepository,
) {
    suspend fun getData(tryNumber: Long): Result {
        val userId = getCurrentUserId()
        val answers = resultsRepository.getAnswers(userId, tryNumber)
        val directions = parseDirections(answers)
        val professions = parseProfessions(answers)

        return Result.Success(directions, professions)
    }

    private suspend fun parseDirections(answers: List<UserAnswer>): List<Direction> {
        val directions = mutableMapOf<String, Int>()
        answers.forEach { answer ->
            directions.addCoefficients(answer)
        }

        return directions.transform {
            Direction(key, "Test description for direction", "http://developer.android.com", value)
        }.sortedByDescending { it.value }.take(3)
    }

    private fun MutableMap<String, Int>.addCoefficients(answer: UserAnswer) {
        answer.score.entries.forEach { score ->
            if (score.value != 0.0) {
                val oldValue = this[score.key] ?: 0
                val newValue = (score.value * 100).toInt()
                this[score.key] = oldValue + newValue
            }
        }
    }

    private suspend fun parseProfessions(answers: List<UserAnswer>): List<Profession> {
        val professions = mutableMapOf<Long, Int>()
        answers.forEach { answer ->
            professions.addProfession(answer)
        }

        val commonCount = professions.values.fold(0) { acc, i -> acc + i }
        return professions.transform {
            val current = value
            val profession = professionsRepository.getProfession(key)
            val score = current * 100 / commonCount
            Profession(profession.name, score)
        }
    }

    private fun MutableMap<Long, Int>.addProfession(answer: UserAnswer) {
        val oldValue = this[answer.professionNumber] ?: 0
        this[answer.professionNumber] = oldValue + 1
    }

    private suspend fun <T, K, V> MutableMap<K, V>.transform(
        transformFunction: suspend MutableMap.MutableEntry<K, V>.() -> T
    ): List<T> {
        val result = mutableListOf<T>()
        this.entries.forEach {
            val newValue = it.transformFunction()
            result.add(newValue)
        }
        return result
    }

    sealed interface Result {
        class Success(
            val directions: List<Direction>,
            val professions: List<Profession>
        ) : Result

        class Error(val message: String) : Result
    }
}


