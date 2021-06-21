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

    private var questionId: Int = 0

    private var questions = listOf<List<String>>()

    private fun saveUserAnswer(answerId: Int) {
        viewModelScope.launch {
            useCase.saveUserAnswer(questionId = questionId - 1, answerId = answerId).also { result ->
                if (result is Result.DataCorrect)
                    navigateForward()

                if (result is Result.Error)
                    sendAction(QuizItemAction.Error(result.message))
            }
        }
    }

    private fun loadQuestions() {
        viewModelScope.launch {
            useCase.getQuestions().also { questionsResponse ->
                if (questionsResponse.result is Result.DataCorrect) {
                    questions = questionsResponse.questions!!
                    if (questions.isNotEmpty()) {
                        navigateForward()
                    }
                }

                if (questionsResponse.result is Result.Error)
                    sendAction(QuizItemAction.Error(questionsResponse.result.message))
            }
        }
    }

    fun clearUsersAttempt() {
        viewModelScope.launch {
            useCase.clearUserAnswers().also { result ->
                if (result is Result.Error)
                    sendAction(QuizItemAction.Error(result.message))
            }
        }
    }

    private fun isLastQuestion(): Boolean =
        questionId == questions.size

    private fun getNextQuestion(): List<String> {
        return questions[questionId++]
    }

    fun onFragmentCreated() {
        loadQuestions()
    }

    fun navigationComplete() {
        state = QuizItemState()
    }

    fun onNextButtonClicked(answerId: Int) {
        saveUserAnswer(answerId)
       // navigateForward()
    }


    private fun navigateForward() {
        if (isLastQuestion()) {
            viewModelScope.launch {
                val tryNumber = useCase.getTryNumber()
                sendAction(QuizItemAction.ToResults(tryNumber))
            }
        } else {
            sendAction(QuizItemAction.ToNextQuestion(getNextQuestion()))
        }
    }

    override fun onReduceState(action: QuizItemAction) = when (action) {
        is QuizItemAction.ToResults -> state.copy(toResults = action.tryNumber)
        is QuizItemAction.ToNextQuestion -> state.copy(answers = action.answers, toNextQuestion = true)
        is QuizItemAction.Error -> state.copy(errorMessage = action.message)
    }

    sealed interface QuizItemAction : BaseAction {
        class ToNextQuestion(val answers: List<String>) : QuizItemAction
        class ToResults(val tryNumber: Long) : QuizItemAction
        class Error(val message: String) : QuizItemAction
    }

    data class QuizItemState(
        val answers: List<String> = emptyList(),
        val toNextQuestion: Boolean = false,
        val toResults: Long = -1,
        val selectedAnswer: Int = -1,
        val errorMessage: String = ""
    ) : BaseState
}
