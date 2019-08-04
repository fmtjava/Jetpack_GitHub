package com.fmt.github.ext

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.fmt.github.App
import com.fmt.github.R
import es.dmoral.toasty.Toasty

fun ImageView.loadUrl(url: String) {
    Glide.with(App.mApplication).load(url)
        .placeholder(R.mipmap.ic_github)
        .into(this)
}

//DataBinding自定义属性
@BindingAdapter("url")
fun loadImage(imageView: ImageView, url: String) {
    Glide.with(App.mApplication).load(url)
        .placeholder(R.mipmap.ic_github)
        .into(imageView)
}

fun hideKeyboard(view: View) {
    val manager = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (view == null) return
    view.windowToken?.let {
        manager.hideSoftInputFromWindow(it, 0)
    }
}

fun errorToast(error: String) {
    Toasty.error(App.mApplication, error, Toast.LENGTH_SHORT, true).show();
}

fun successToast(error: String) {
    Toasty.success(App.mApplication, error, Toast.LENGTH_SHORT, true).show()
}