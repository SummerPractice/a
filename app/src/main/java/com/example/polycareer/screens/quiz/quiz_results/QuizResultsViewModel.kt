package com.example.polycareer.screens.quiz.quiz_results

import androidx.lifecycle.viewModelScope
import com.example.polycareer.base.BaseAction
import com.example.polycareer.base.BaseState
import com.example.polycareer.base.BaseViewModel
import com.example.polycareer.domain.model.Direction
import com.example.polycareer.domain.model.Profession
import com.example.polycareer.domain.usecase.QuizResultUseCase
import kotlinx.coroutines.launch

class QuizResultsViewModel(
    private val useCase: QuizResultUseCase
) : BaseViewModel<QuizResultsViewModel.QuizResultState, QuizResultsViewModel.QuizResultAction>(
    QuizResultState()
) {
    var tryNumber: Long = -1

    override fun onLoadData() {
        viewModelScope.launch {
            sendAction(QuizResultAction.ShowLoader)

            useCase.getData(tryNumber).also { result ->
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
            isLoad = false,
            directions = action.directions,
            professions = action.professions
        )
        is QuizResultAction.Error -> state.copy(
            isLoad = false,
            error = action.message
        )

        QuizResultAction.ShowLoader -> state.copy(
            isLoad = true
        )
    }

    sealed interface QuizResultAction : BaseAction {
        object ShowLoader : QuizResultAction

        class ShowResults(
            val directions: List<Direction>,
            val professions: List<Profession>
        ) : QuizResultAction

        class Error(val message: String) : QuizResultAction
    }

    data class QuizResultState(
        val isLoad: Boolean = true,
        val directions: List<Direction> = emptyList(),
        val professions: List<Profession> = emptyList(),
        val error: String = ""
    ) : BaseState
}