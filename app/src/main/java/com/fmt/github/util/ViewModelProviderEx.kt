package com.fmt.github.util

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fmt.github.App

//获取ViewModel
fun <VM : ViewModel> of(activity: AppCompatActivity, modelClass: Class<VM>): VM {
    val viewModelFactory = ViewModelProvider.AndroidViewModelFactory(App.mApplication)
    val viewModelProvider = ViewModelProvider(activity, viewModelFactory)
    return viewModelProvider.get(modelClass)
}