package com.example.polycareer.screens.main.quiz_results

import androidx.lifecycle.viewModelScope
import com.example.polycareer.base.BaseAction
import com.example.polycareer.base.BaseState
import com.example.polycareer.base.BaseViewModel
import com.example.polycareer.domain.usecase.QuizResultUseCase
import kotlinx.coroutines.launch

class QuizResultsViewModel(
    private val useCase: QuizResultUseCase
) : BaseViewModel<QuizResultsViewModel.QuizResultState, QuizResultsViewModel.QuizResultAction>(
    QuizResultState()
) {

    fun getData() {
        viewModelScope.launch {
            val directions = mapOf(
                "PI" to 1,
                "Math" to 2,
            )

            val professions = mapOf(
                "Проектирование" to 50,
                "Искусственный интеллект" to 25,
                "Big Data" to 10,
                "Iot" to 15
            )

            sendAction(QuizResultAction.ShowResults(directions, professions))
        }
    }

    override fun onReduceState(action: QuizResultAction): QuizResultState = when (action) {
        is QuizResultAction.ShowResults -> state.copy(
            directions = action.directions,
            professions = action.professions,
            error = ""
        )
        is QuizResultAction.Error -> state.copy(
            directions = emptyMap(),
            professions = emptyMap(),
            error = action.message
        )
    }

    sealed interface QuizResultAction : BaseAction {
        class ShowResults(
            val directions: Map<String, Int>,
            val professions: Map<String, Int>
        ) : QuizResultAction

        class Error(val message: String) : QuizResultAction
    }

    data class QuizResultState(
        val directions: Map<String, Int> = emptyMap(),
        val professions: Map<String, Int> = emptyMap(),
        val error: String = ""
    ) : BaseState
}