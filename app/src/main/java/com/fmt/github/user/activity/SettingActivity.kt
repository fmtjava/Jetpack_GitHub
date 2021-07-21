package com.fmt.github.user.activity

import android.util.Log
import com.fmt.github.R
import com.fmt.github.base.activity.BaseActivity
import com.fmt.github.fps.FpsMonitor
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : BaseActivity() {

    override fun getLayoutId(): Int = R.layout.activity_setting

    override fun initView() {
        mToolbar.setNavigationOnClickListener { finish() }
        mSwitchButton.setOnCheckedChangeListener { _, _ ->
            FpsMonitor.toggle()
        }
    }
}