package com.example.polycareer.domain.usecase

import com.example.polycareer.domain.model.Direction
import com.example.polycareer.domain.model.Profession
import com.example.polycareer.domain.model.UserAnswer
import com.example.polycareer.domain.repository.DirectionRepository
import com.example.polycareer.domain.repository.ProfessionRepository
import com.example.polycareer.domain.repository.ResultsRepository
import com.example.polycareer.domain.repository.UserCache

class QuizResultUseCase(
    private val resultsRepository: ResultsRepository,
    private val professionsRepository: ProfessionRepository,
    private val directionRepository: DirectionRepository,
    private val userCache: UserCache
) {
    suspend fun getData(tryNumber: Long): Result {
        val userId = userCache.getCurrentUserId()
        val answers = resultsRepository.getAnswers(userId, tryNumber)
        val directions = parseDirections(answers)
        val professions = parseProfessions(answers)

        return Result.Success(directions, professions)
    }

    private suspend fun parseDirections(answers: List<UserAnswer>): List<Direction> {
        val directions = mutableMapOf<Long, Int>()
        answers.forEach { answer ->
            directions.addCoefficients(answer)
        }

        return directions.transform {
            val directionInfo = directionRepository.getDirection(key)
            Direction(directionInfo.name, directionInfo.description, directionInfo.url, value)
        }.sortedByDescending { it.value }.take(3)
    }

    private fun MutableMap<Long, Int>.addCoefficients(answer: UserAnswer) {
        answer.score.entries.forEach { score ->
            if (score.value != 0.0) {
                val oldValue = this[score.key] ?: 0
                val newValue = (score.value * 100).toInt()
                this[score.key] = oldValue + newValue
            }
        }
    }

    private suspend fun parseProfessions(answers: List<UserAnswer>): List<Profession> {
        val countProfessions = mutableMapOf<Long, Int>()
        answers.forEach { answer -> countProfessions.addProfession(answer) }

        val professions = countProfessions.transform {
            val profession = professionsRepository.getProfession(key)
            val score = value * 100 / profession.countOfAnswer
            Profession(profession.name, score)
        }.sortedByDescending { it.percent }.take(3)

        val commonScore = professions.fold(0) { acc, profession -> acc + profession.percent }
        return professions.map { Profession(it.title, it.percent * 100 / commonScore) }
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


