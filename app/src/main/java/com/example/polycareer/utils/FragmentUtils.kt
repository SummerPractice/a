package com.example.polycareer.utils

import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModel

fun <T : ViewModel> EditText.setValidateRule(viewModel: T, validator: T.() -> Unit) {
    this.doOnTextChanged { _, _, _, _ -> viewModel.validator() }
}

fun <T> setValueByCondition(condition: Boolean, message: T): T? = if (condition) message else null

val EditText.value: String
    get() = this.text.toString()