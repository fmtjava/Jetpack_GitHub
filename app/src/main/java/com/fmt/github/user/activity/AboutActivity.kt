package com.fmt.github.user.activity

import com.fmt.github.R
import com.fmt.github.base.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : BaseActivity() {

    override fun getLayoutId(): Int = R.layout.activity_about

    override fun initView() {
        mToolbar.setNavigationOnClickListener { finish() }
    }
}