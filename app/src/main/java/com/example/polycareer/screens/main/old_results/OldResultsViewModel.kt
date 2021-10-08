package com.example.polycareer.screens.main.old_results

import androidx.lifecycle.viewModelScope
import com.example.polycareer.base.BaseAction
import com.example.polycareer.base.BaseState
import com.example.polycareer.base.BaseViewModel
import com.example.polycareer.domain.model.UserResultInfo
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
            resultsInfo = action.resultsInfo,
            error = false
        )
        is OldResultsAction.Error -> state.copy(
            resultsInfo = emptyList(),
            error = true
        )
    }

    sealed interface OldResultsAction : BaseAction {
        class ShowResults(
            val resultsInfo: List<UserResultInfo>
        ) : OldResultsAction

        class Error(val message: String) : OldResultsAction
    }

    data class OldResultsState(
        val resultsInfo: List<UserResultInfo> = emptyList(),
        val error: Boolean = false
    ) : BaseState
}