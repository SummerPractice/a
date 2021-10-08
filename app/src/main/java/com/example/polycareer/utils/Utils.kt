package com.example.polycareer.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

@Suppress("detekt.UnsafeCast")
fun <T> MutableLiveData<T>.asLiveData() = this as LiveData<T>