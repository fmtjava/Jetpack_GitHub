package com.fmt.github.repos.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.fmt.github.R
import com.fmt.github.ext.loadUrl
import com.fmt.github.repos.model.ReposItemModel

class ReposAdapter : BaseQuickAdapter<ReposItemModel, BaseViewHolder>(R.layout.layout_repos) {

    override fun convert(helper: BaseViewHolder, item: ReposItemModel) {
        helper.setText(R.id.tv_repos_name, item.name)
            .setText(R.id.tv_repos_des, item.description)
            .setText(R.id.tv_start_num, item.stargazers_count.toString())
            .setText(R.id.tv_fork_num, item.forks.toString())
            .setText(R.id.tv_author, item.owner.login)
            .setVisible(R.id.tv_language, !item.language.isNullOrEmpty())
            .setVisible(R.id.view_language_bg, !item.language.isNullOrEmpty())
        helper.getView<ImageView>(R.id.iv_head).loadUrl(item.owner.avatar_url)

        item.language?.let {
            helper.setText(R.id.tv_language, it)
            when (it) {
                "Java" -> helper.setBackgroundRes(R.id.view_language_bg, R.drawable.shape_java_circular_bg)
                "Kotlin" -> helper.setBackgroundRes(R.id.view_language_bg, R.drawable.shape_kotlin_circular_bg)
                "Swift" -> helper.setBackgroundRes(R.id.view_language_bg, R.drawable.shape_swift_circular_bg)
                "HTML" -> helper.setBackgroundRes(R.id.view_language_bg, R.drawable.shape_html_circular_bg)
                "JavaScript" -> helper.setBackgroundRes(R.id.view_language_bg, R.drawable.shape_js_circular_bg)
                "CSS" -> helper.setBackgroundRes(R.id.view_language_bg, R.drawable.shape_css_circular_bg)
                "PHP" -> helper.setBackgroundRes(R.id.view_language_bg, R.drawable.shape_php_circular_bg)
                "TypeScript" -> helper.setBackgroundRes(R.id.view_language_bg, R.drawable.shape_ts_circular_bg)
                "Python" -> helper.setBackgroundRes(R.id.view_language_bg, R.drawable.shape_python_circular_bg)
                "C" -> helper.setBackgroundRes(R.id.view_language_bg, R.drawable.shape_c_circular_bg)
                "C++" -> helper.setBackgroundRes(R.id.view_language_bg, R.drawable.shape_c_plus_circular_bg)
                "C#" -> helper.setBackgroundRes(R.id.view_language_bg, R.drawable.shape_co_circular_bg)
                "Objective-C" -> helper.setBackgroundRes(R.id.view_language_bg, R.drawable.shape_oc_circular_bg)
                "Go" -> helper.setBackgroundRes(R.id.view_language_bg, R.drawable.shape_go_circular_bg)
                "R" -> helper.setBackgroundRes(R.id.view_language_bg, R.drawable.shape_r_circular_bg)
                "Shell" -> helper.setBackgroundRes(R.id.view_language_bg, R.drawable.shape_shell_circular_bg)
                "Ruby" -> helper.setBackgroundRes(R.id.view_language_bg, R.drawable.shape_ruby_circular_bg)
                "Vue" -> helper.setBackgroundRes(R.id.view_language_bg, R.drawable.shape_vue_circular_bg)
                "Dart" -> helper.setBackgroundRes(R.id.view_language_bg, R.drawable.shape_dart_circular_bg)
                else -> helper.setBackgroundRes(R.id.view_language_bg, R.drawable.shape_html_circular_bg)
            }
        }
    }
}