package com.fmt.github.base.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    val mStateLiveData = MutableLiveData<StateActionEvent>()//通用事件模型驱动(如：显示对话框、取消对话框、错误提示)

    fun launch(block: suspend CoroutineScope.() -> Unit) {//使用协程封装统一的网络请求处理

        viewModelScope.launch {
            //ViewModel自带的viewModelScope.launch,会在页面销毁的时候自动取消请求,有效封装内存泄露
            try {
                mStateLiveData.value = StateActionEvent(StateAction.LOADING)
                block()
                mStateLiveData.value = StateActionEvent(StateAction.SUCCESS)
            } catch (e: Exception) {
                mStateLiveData.value = StateActionEvent(StateAction.ERROR, e.message)
            }
        }

    }

}