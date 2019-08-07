package com.fmt.github.ext

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import com.bumptech.glide.Glide
import com.fmt.github.App
import com.fmt.github.AppContext
import com.fmt.github.R
import com.fmt.github.repos.model.ReposItemModel

//DataBinding自定义属性
@BindingAdapter("url")
fun loadImage(imageView: ImageView, url: String) {
    Glide.with(AppContext).load(url)
        .placeholder(R.mipmap.ic_github)
        .into(imageView)
}

//DataBinding类型转换
@BindingConversion
fun setReposLanguageBg(reposItemModel: ReposItemModel): Drawable? =
    reposItemModel.language?.let {
        when (it) {
            "Java" -> ContextCompat.getDrawable(AppContext, R.drawable.shape_java_circular_bg)
            "Kotlin" -> ContextCompat.getDrawable(AppContext, R.drawable.shape_kotlin_circular_bg)
            "Swift" -> ContextCompat.getDrawable(AppContext, R.drawable.shape_swift_circular_bg)
            "HTML" -> ContextCompat.getDrawable(AppContext, R.drawable.shape_html_circular_bg)
            "JavaScript" -> ContextCompat.getDrawable(AppContext, R.drawable.shape_js_circular_bg)
            "CSS" -> ContextCompat.getDrawable(AppContext, R.drawable.shape_css_circular_bg)
            "PHP" -> ContextCompat.getDrawable(AppContext, R.drawable.shape_php_circular_bg)
            "TypeScript" -> ContextCompat.getDrawable(AppContext, R.drawable.shape_ts_circular_bg)
            "Python" -> ContextCompat.getDrawable(AppContext, R.drawable.shape_python_circular_bg)
            "C" -> ContextCompat.getDrawable(AppContext, R.drawable.shape_c_circular_bg)
            "C++" -> ContextCompat.getDrawable(AppContext, R.drawable.shape_c_plus_circular_bg)
            "C#" -> ContextCompat.getDrawable(AppContext, R.drawable.shape_co_circular_bg)
            "Objective-C" -> ContextCompat.getDrawable(AppContext, R.drawable.shape_oc_circular_bg)
            "Go" -> ContextCompat.getDrawable(AppContext, R.drawable.shape_go_circular_bg)
            "R" -> ContextCompat.getDrawable(AppContext, R.drawable.shape_r_circular_bg)
            "Shell" -> ContextCompat.getDrawable(AppContext, R.drawable.shape_shell_circular_bg)
            "Ruby" -> ContextCompat.getDrawable(AppContext, R.drawable.shape_ruby_circular_bg)
            "Vue" -> ContextCompat.getDrawable(AppContext, R.drawable.shape_vue_circular_bg)
            "Dart" -> ContextCompat.getDrawable(AppContext, R.drawable.shape_dart_circular_bg)
            else -> ContextCompat.getDrawable(AppContext, R.drawable.shape_html_circular_bg)
        }
    }