package com.example.polycareer.domain.usecase

import com.example.polycareer.domain.model.UserAnswer
import com.example.polycareer.domain.repository.ProfessionRepository
import com.example.polycareer.domain.repository.ResultsRepository

class QuizResultUseCase(
    private val resultsRepository: ResultsRepository,
    private val professionsRepository: ProfessionRepository,
) {
    suspend fun getData(): Result {
        val answers = resultsRepository.getAnswers()
        val directions = parseDirections(answers)
        val professions = parseProfessions(answers)

        return Result.Success(directions, professions)
    }

    private fun parseDirections(answers: List<UserAnswer>): Map<String, Int> {
        val directions = mutableMapOf<String, Int>()
        answers.forEach { answer ->
            directions.addCoefficients(answer)
        }
        return directions
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

    private suspend fun parseProfessions(answers: List<UserAnswer>): Map<String, Int> {
        val professions = mutableMapOf<Long, Int>()
        answers.forEach { answer ->
            professions.addProfession(answer)
        }

        return professions.transform {
            val current = value
            val profession = professionsRepository.getProfession(key)
            val score = current * 100 / profession.countOfAnswer
            return@transform profession.name to score
        }
    }

    private fun MutableMap<Long, Int>.addProfession(answer: UserAnswer) {
        val oldValue = this[answer.professionNumber] ?: 0
        this[answer.professionNumber] = oldValue + 1
    }

    private suspend fun MutableMap<Long, Int>.transform(
        transformFunction: suspend MutableMap.MutableEntry<Long, Int>.() -> Pair<String, Int>
    ): Map<String, Int> {
        val result = mutableMapOf<String, Int>()
        this.entries.forEach {
            val pair = it.transformFunction()
            result[pair.first] = pair.second
        }
        return result
    }

    sealed interface Result {
        class Success(val directions: Map<String, Int>, val professions: Map<String, Int>) : Result
        class Error(val message: String) : Result
    }
}


