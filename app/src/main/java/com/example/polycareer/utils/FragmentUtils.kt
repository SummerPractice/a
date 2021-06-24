package com.example.polycareer.utils

import android.widget.EditText
import androidx.annotation.IdRes
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.NavHostFragment

fun <T : ViewModel> EditText.setValidateRule(viewModel: T, validator: T.() -> Unit) {
    this.doOnTextChanged { _, _, _, _ -> viewModel.validator() }
}

fun <T> setValueByCondition(condition: Boolean, message: T): T? = if (condition) message else null

val EditText.value: String
    get() = this.text.toString()

fun Fragment.openScreen(@IdRes destination: Int) {
    val navController = NavHostFragment.findNavController(this)
    navController.navigate(destination)
}