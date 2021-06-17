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
            useCase.getData().also { result ->
                when (result) {
                    is QuizResultUseCase.Result.Success -> sendAction(
                        QuizResultAction.ShowResults(
                            result.directions,
                            result.professions
                        )
                    )
                    is QuizResultUseCase.Result.Error ->
                        sendAction(QuizResultAction.Error(result.message))
                }
            }
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