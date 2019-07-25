package com.fmt.github

import android.content.Intent
import com.fmt.github.base.activity.BaseActivity
import com.fmt.github.data.db.DBInstance
import com.fmt.github.home.activity.HomeActivity
import com.fmt.github.user.activity.LoginActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WelcomeActivity : BaseActivity() {

    override fun getLayoutId(): Int = R.layout.activity_welcome

    override fun initView() {
        launch {
            delay(1000)
            val userList = DBInstance.mAppDataBase.getUserDao().getAll()
            if (userList == null || userList.isEmpty()) {
                go2Activity(LoginActivity::class.java)
            } else {
                go2Activity(HomeActivity::class.java)
            }
        }
    }

    private fun go2Activity(clazz: Class<*>) {
        Intent(this@WelcomeActivity, clazz).run {
            startActivity(this)
            finish()
        }
    }
}