package com.example.polycareer.screens.quiz.quiz_item

import androidx.lifecycle.viewModelScope
import com.example.polycareer.base.BaseAction
import com.example.polycareer.base.BaseState
import com.example.polycareer.base.BaseViewModel
import com.example.polycareer.domain.usecase.QuizItemUseCase
import kotlinx.coroutines.launch

class QuizItemViewModel(
    private val useCase: QuizItemUseCase
) : BaseViewModel<QuizItemViewModel.QuizItemState, QuizItemViewModel.QuizItemAction>(QuizItemState()) {

    private var questionId: Int = -1

    private var questions = listOf<List<String>>()

    private fun deleteUnfinishedTests() {
        viewModelScope.launch {
            useCase.deleteUnfinishedTests()
                .also { result ->
                    if (result is QuizItemUseCase.Result.Error)
                        sendAction(QuizItemAction.Error(result.message))
                }
        }
    }

    private fun saveUserAnswer(answerId: Int) {
        viewModelScope.launch {
            useCase
                .saveUserAnswer(questionId = questionId, answerId = answerId)
                .also { result ->
                    if (result is QuizItemUseCase.Result.Success)
                        navigateForward()

                    if (result is QuizItemUseCase.Result.Error)
                        sendAction(QuizItemAction.Error(result.message))
                }
        }
    }

    private fun deleteLastAnswer() {
        viewModelScope.launch {
            useCase.clearUserLastAnswer().also { result ->
                if (result is QuizItemUseCase.Result.Success)
                    navigateBackward()

                if (result is QuizItemUseCase.Result.Error)
                    sendAction(QuizItemAction.Error(result.message))
            }
        }
    }

    private fun loadQuestions() {
        viewModelScope.launch {
            useCase.getQuestions().also { result ->
                if (result is QuizItemUseCase.Result.DataCorrect) {
                    questions = result.questionsResponse.questions
                    if (questions.isNotEmpty()) {
                        navigateForward()
                    }
                }

                if (result is QuizItemUseCase.Result.Error)
                    sendAction(QuizItemAction.Error(result.message))
            }
        }
    }

    private fun isLastQuestion(): Boolean =
        questionId == questions.size - 1


    private fun isFirstQuestion(): Boolean =
        questionId <= 0

    private fun getNextQuestion(): List<String> = questions[++questionId]


    private fun getPreviousQuestion(): List<String> = questions[--questionId]

    fun onFragmentCreated() {
        if (questions.isEmpty()) {
            deleteUnfinishedTests()
            loadQuestions()
        }
    }

    fun onReload() {
        loadQuestions()
    }

    fun toPreviousQuestion() {
//        if (questions.isEmpty()) {
//            return
//        }
        if (isFirstQuestion()) {
            sendAction(QuizItemAction.ToMenu)
        } else {
            deleteLastAnswer()
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
            viewModelScope.launch {
                val tryNumber = useCase.getTryNumber()
                sendAction(QuizItemAction.ToResults(tryNumber))
            }
        } else {
            sendAction(QuizItemAction.ToNextQuestion(getNextQuestion()))
        }
    }

    private fun navigateBackward() {
        sendAction(QuizItemAction.ToNextQuestion(getPreviousQuestion()))
    }

    override fun onReduceState(action: QuizItemAction) = when (action) {
        is QuizItemAction.ToResults -> state.copy(toResults = action.tryNumber)
        is QuizItemAction.ToNextQuestion -> state.copy(
            answers = action.answers,
            toNextQuestion = true,
            progress = 100 / questions.size * questionId
        )
        is QuizItemAction.ToMenu -> state.copy(toMenu = true)
        is QuizItemAction.Error -> state.copy(errorMessage = action.message)
    }

    fun onFragmentDestroyed() {
        if (questionId > 0) {
            deleteUnfinishedTests()
        }
    }


    sealed interface QuizItemAction : BaseAction {
        class ToNextQuestion(val answers: List<String>) : QuizItemAction
        class ToResults(val tryNumber: Long) : QuizItemAction
        class Error(val message: String) : QuizItemAction
        object ToMenu : QuizItemAction
    }

    data class QuizItemState(
        val answers: List<String> = emptyList(),
        val toNextQuestion: Boolean = false,
        val toResults: Long = -1,
        val toMenu: Boolean = false,
        val selectedAnswer: Int = -1,
        val errorMessage: String = "",
        val progress: Int = 0,
    ) : BaseState
}
