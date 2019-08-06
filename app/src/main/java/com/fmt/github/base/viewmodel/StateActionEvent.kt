package com.fmt.github.base.viewmodel

//定义网络请求状态的事件驱动模型
data class StateActionEvent(val stateAction: StateAction, val message: String? = "")