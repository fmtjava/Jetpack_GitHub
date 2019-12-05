package com.fmt.github

import android.content.Intent
import com.afollestad.assent.Permission
import com.afollestad.assent.askForPermissions
import com.fmt.github.base.activity.BaseDataBindActivity
import com.fmt.github.databinding.ActivityWelcomeBinding
import com.fmt.github.ext.getVersionName
import com.fmt.github.ext.otherwise
import com.fmt.github.ext.yes
import com.fmt.github.home.activity.HomeActivity
import com.fmt.github.user.activity.LoginActivity
import com.fmt.github.user.dao.UserDao
import com.jaredrummler.android.widget.AnimatedSvgView
import kotlinx.android.synthetic.main.activity_welcome.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class WelcomeActivity : BaseDataBindActivity<ActivityWelcomeBinding>() {

    private val mUserDao: UserDao by inject()

    override fun getLayoutId(): Int = R.layout.activity_welcome

    override fun initView() {
        mAnimatedSvgView.setViewportSize(512f, 512f)
        mAnimatedSvgView.setOnStateChangeListener {
            (it == AnimatedSvgView.STATE_FINISHED).yes {
                askForPermission()
            }
        }
        mAnimatedSvgView.start()
    }

    override fun initData() {
        mDataBind.versionName = getVersionName()
    }

    private fun askForPermission() {
        askForPermissions(Permission.READ_PHONE_STATE) { result ->
            result.isAllGranted(Permission.READ_PHONE_STATE).yes {
                checkIsLogin()
            }.otherwise {
                finish()
            }
        }
    }

    private fun checkIsLogin() {
        launch {
            delay(500)//挂起,但不会阻塞,后续通过resumeWith恢复执行
            val userList = mUserDao.getAll()
            (userList.isEmpty()).yes {
                go2Activity(LoginActivity::class.java)
            }.otherwise {
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