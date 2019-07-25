package com.fmt.github.base.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    val mErrorLiveData = MutableLiveData<String>()//统一错误提示

    fun launch(block: suspend CoroutineScope.() -> Unit) {

        viewModelScope.launch {
            try {
                block()
            } catch (e: Exception) {
                mErrorLiveData.value = e.message
            }
        }

    }

}