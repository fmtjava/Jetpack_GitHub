package com.fmt.github.ext

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun hideKeyboard(view: View) {
    val manager = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (view == null) return
    view.windowToken?.let {
        manager.hideSoftInputFromWindow(it, 0)
    }
}