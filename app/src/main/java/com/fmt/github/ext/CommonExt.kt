package com.fmt.github.ext

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.fmt.github.AppContext
import com.fmt.github.R

fun ImageView.loadUrl(url: String) {
    Glide.with(AppContext).load(url)
        .placeholder(R.mipmap.ic_github)
        .into(this)
}


