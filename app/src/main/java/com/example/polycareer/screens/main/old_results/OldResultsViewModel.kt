package com.example.polycareer.screens.main.old_results

import androidx.lifecycle.viewModelScope
import com.example.polycareer.base.BaseAction
import com.example.polycareer.base.BaseState
import com.example.polycareer.base.BaseViewModel
import com.example.polycareer.domain.model.UserResult
import com.example.polycareer.domain.usecase.OldResultsUseCase
import kotlinx.coroutines.launch

class OldResultsViewModel(
    private val useCase: OldResultsUseCase
) : BaseViewModel<OldResultsViewModel.OldResultsState, OldResultsViewModel.OldResultsAction>(
    OldResultsState()
) {
    fun getData() {
        viewModelScope.launch {
            useCase.getData().also { result ->
                when (result) {
                    is OldResultsUseCase.Result.Success -> sendAction(
                        OldResultsAction.ShowResults(
                            result.oldResults
                        )
                    )
                    is OldResultsUseCase.Result.Error ->
                        sendAction(OldResultsAction.Error(result.message))
                }
            }
        }
    }

    override fun onReduceState(action: OldResultsAction): OldResultsState = when (action) {
        is OldResultsAction.ShowResults -> state.copy(
            oldResults = action.oldResults,
            error = ""
        )
        is OldResultsAction.Error -> state.copy(
            oldResults = emptyList(),
            error = action.message
        )
    }

    sealed interface OldResultsAction : BaseAction {
        class ShowResults(
            val oldResults: List<UserResult>
        ) : OldResultsAction

        class Error(val message: String) : OldResultsAction
    }

    data class OldResultsState(
        val oldResults: List<UserResult> = emptyList(),
        val error: String = ""
    ) : BaseState
}