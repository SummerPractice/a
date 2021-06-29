package com.example.polycareer.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.polycareer.utils.asLiveData
import kotlin.properties.Delegates

abstract class BaseViewModel<ViewState : BaseState, ViewAction : BaseAction>(initialState: ViewState) :
    ViewModel() {

    private val stateMutableLiveData = MutableLiveData<ViewState>()
    val stateLiveData = stateMutableLiveData.asLiveData()


    protected var state by Delegates.observable(initialState) { _, _, new ->
        stateMutableLiveData.value = new
    }

    fun sendAction(viewAction: ViewAction) {
        state = onReduceState(viewAction)
    }

    fun loadData() {
        onLoadData()
    }

    protected open fun onLoadData() {}

    protected abstract fun onReduceState(action: ViewAction): ViewState

}