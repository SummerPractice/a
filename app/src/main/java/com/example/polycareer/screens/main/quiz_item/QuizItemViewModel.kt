package com.example.polycareer.screens.main.quiz_item

import androidx.lifecycle.viewModelScope
import com.example.polycareer.base.BaseAction
import com.example.polycareer.base.BaseState
import com.example.polycareer.base.BaseViewModel
import com.example.polycareer.domain.model.Result
import com.example.polycareer.domain.usecase.QuizItemUseCase
import kotlinx.coroutines.launch

class QuizItemViewModel(
    private val useCase: QuizItemUseCase
): BaseViewModel<QuizItemViewModel.QuizItemState, QuizItemViewModel.QuizItemAction>(QuizItemState()) {

    private var answerId: Int = 0

    private var questions = listOf<List<String>>()

    fun saveUserAnswer(answerId: Int) {
        viewModelScope.launch {
            useCase.saveUserAnswer(answerId).also { result ->
                if (result is Result.DataCorrect)
                    navigateForward()

                if (result is Result.Error)
                    sendAction(QuizItemAction.Error(result.message))
            }
        }
    }

    fun loadQuestions() {
        viewModelScope.launch {
            useCase.getQuestions().also { questionsResponse ->
                if (questionsResponse.result is Result.DataCorrect)
                    questions = questionsResponse.questions!!

                if (questionsResponse.result is Result.Error)
                    sendAction(QuizItemAction.Error(questionsResponse.result.message))
            }
        }
    }

    fun isLastQuestion(): Boolean =
        answerId == questions.size

    fun getNextQuestion(): List<String> {
        return questions.get(answerId++)
    }

    fun onFragmentCreated() {
        loadQuestions()
        if (questions.isNotEmpty()) {
            navigateForward()
        }
    }

    fun navigationComplete() {
        state = QuizItemState()
    }

    fun onNextButtonClicked(answerId: Int) {
        saveUserAnswer(answerId)
    }


    private fun navigateForward() {
        if (isLastQuestion()) {
            sendAction(QuizItemAction.ToResults)
        } else {
            sendAction(QuizItemAction.ToNextQuestion(getNextQuestion()))
        }
    }

    override fun onReduceState(action: QuizItemAction) = when (action) {
        is QuizItemAction.ToResults -> state.copy(toResults = true)
        is QuizItemAction.ToNextQuestion -> state.copy(answers = action.answers, toNextQuestion = true)
        is QuizItemAction.Error -> state.copy(errorMessage = action.message)
    }

    sealed interface QuizItemAction : BaseAction {
        class ToNextQuestion(val answers: List<String>) : QuizItemAction
        object ToResults : QuizItemAction
        class Error(val message: String) : QuizItemAction
    }

    data class QuizItemState(
        val answers: List<String> = emptyList(),
        val toNextQuestion: Boolean = false,
        val toResults: Boolean = false,
        val selectedAnswer: Int = -1,
        val errorMessage: String = ""
    ) : BaseState
}
