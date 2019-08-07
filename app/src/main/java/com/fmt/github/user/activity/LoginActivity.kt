package com.fmt.github.user.activity

import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import com.fmt.github.R
import com.fmt.github.base.activity.BaseVMActivity
import com.fmt.github.constant.Constant
import com.fmt.github.data.storage.Preference
import com.fmt.github.home.activity.HomeActivity
import com.fmt.github.user.db.User
import com.fmt.github.user.model.AuthorizationRespModel
import com.fmt.github.user.model.UserModel
import com.fmt.github.user.viewmodel.UserViewModel
import com.fmt.github.ext.of
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseVMActivity<UserViewModel>() {

    var mToken by Preference(Constant.USER_TOKEN, "")
    var mUserName by Preference(Constant.USER_NAME, "")
    var mPassword by Preference(Constant.USER_PASSWORD, "")

    var mAuthId = 0

    override fun getLayoutId(): Int = R.layout.activity_login

    override fun initViewModel(): UserViewModel = of(this, UserViewModel::class.java)

    override fun initView() {
        mSignInBt.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val userName = mUserNameEditText.text.toString().trim()
        val password = mPasswordEditText.text.toString().trim()
        if (userName.isNullOrEmpty()) {
            mUserNameInputLayout.error = getString(R.string.username_not_null)
            mUserNameInputLayout.isErrorEnabled = true
            return
        } else {
            mUserNameInputLayout.isErrorEnabled = false
        }
        if (password.isNullOrEmpty()) {
            mPasswordInputLayout.error = getString(R.string.password_not_null)
            mPasswordInputLayout.isErrorEnabled = true
            return
        } else {
            mPasswordInputLayout.isErrorEnabled = false
        }
        mUserName = userName
        mPassword = password
        mSignInBt.visibility = View.GONE
        mProgressBar.visibility = View.VISIBLE
        mViewModel.createOrGetAuthorization().observe(this, Observer<AuthorizationRespModel> {
            //保存授权后的Token和ID
            mToken = it.token
            mAuthId = it.id
            //获取用户信息
            getUserInfo()
        })
    }

    private fun getUserInfo() {
        mViewModel.getUser().observe(this, Observer<UserModel> {
            saveUserInfo(it)
        })
    }

    private fun saveUserInfo(userModel: UserModel) {
        User(mAuthId, userModel.login, userModel.avatar_url).apply {
            mViewModel.saveLocalUser(this@apply)
            go2MainActivity()
        }
    }

    private fun go2MainActivity() {
        Intent(this, HomeActivity::class.java).run {
            startActivity(this)
            finish()
        }
    }

    override fun handleError() {
        mSignInBt.visibility = View.VISIBLE
        mProgressBar.visibility = View.GONE
    }
}