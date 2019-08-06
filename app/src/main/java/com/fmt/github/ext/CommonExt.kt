package com.fmt.github.ext

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import com.bumptech.glide.Glide
import com.fmt.github.App
import com.fmt.github.R
import com.fmt.github.repos.model.ReposItemModel
import es.dmoral.toasty.Toasty

fun ImageView.loadUrl(url: String) {
    Glide.with(App.mApplication).load(url)
        .placeholder(R.mipmap.ic_github)
        .into(this)
}


