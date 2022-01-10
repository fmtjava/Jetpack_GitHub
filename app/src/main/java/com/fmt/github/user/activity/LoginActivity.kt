package com.fmt.github.user.activity

import android.content.Intent
import android.view.View
import com.fmt.github.R
import com.fmt.github.base.activity.BaseDataBindVMActivity
import com.fmt.github.base.viewmodel.BaseViewModel
import com.fmt.github.config.Configs
import com.fmt.github.config.Settings
import com.fmt.github.databinding.ActivityLoginBinding
import com.fmt.github.ext.otherwise
import com.fmt.github.ext.startActivity
import com.fmt.github.ext.yes
import com.fmt.github.home.activity.HomeActivity
import com.fmt.github.user.model.UserLoginModel
import com.fmt.github.user.model.UserModel
import com.fmt.github.user.model.db.User
import com.fmt.github.user.viewmodel.UserViewModel
import com.fmt.github.utils.AppOpener
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class LoginActivity : BaseDataBindVMActivity<ActivityLoginBinding>() {

    private val mViewModel: UserViewModel by viewModel()

    private val mUserLoginModel: UserLoginModel by lazy { UserLoginModel() }

    override fun getLayoutId(): Int = R.layout.activity_login

    override fun getViewModel(): BaseViewModel = mViewModel

    override fun initView() {
        mDataBind.mSignInBt.setOnClickListener {
            login()
        }
        mDataBind.mOAuthLoginIv.setOnClickListener {
            loginByOAuth()
        }
    }

    override fun initData() {
        mDataBind.userLoginModel = mUserLoginModel
    }

    private fun login() {
        val username = mUserLoginModel.username.get().toString()
        val password = mUserLoginModel.password.get().toString()
        username.isEmpty().yes {
            mDataBind.mUserNameInputLayout.error = getString(R.string.username_not_null)
            mDataBind.mUserNameInputLayout.isErrorEnabled = true
        }.otherwise {
            mDataBind.mUserNameInputLayout.isErrorEnabled = false
            password.isEmpty().yes {
                mDataBind.mPasswordInputLayout.error = getString(R.string.password_not_null)
                mDataBind.mPasswordInputLayout.isErrorEnabled = true
            }.otherwise {
                mDataBind.mPasswordInputLayout.isErrorEnabled = false
                mDataBind.mSignInBt.visibility = View.GONE
                mDataBind.mProgressBar.visibility = View.VISIBLE
                Settings.Account.userName = username
                Settings.Account.password = password
                createOrGetAuthorization()
            }
        }

    }

    private fun loginByOAuth() {
        val randomState = UUID.randomUUID().toString()
        val url = "${Configs.GITHUB_BASE_URL}login/oauth/authorize" +
                "?client_id=" + Configs.CLIENT_ID +
                "&scope=" + Configs.OAUTH2_SCOPE +
                "&state=" + randomState
        AppOpener.openInBrowser(this, url)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let {
            val uri = it.data
            if (uri != null) {
                val code = uri.getQueryParameter("code")
                val state = uri.getQueryParameter("state")
                getToken(code, state)
            }
        }
    }

    private fun createOrGetAuthorization() {
        mViewModel.createOrGetAuthorization().observeKt {
            //保存授权后的Token和ID
            Settings.Account.token = it.token
            //获取用户信息
            getUserInfo()
        }
    }

    private fun getToken(code: String?, state: String?) {
        if (code != null && state != null) {
            mDataBind.mOAuthProgress.visibility = View.VISIBLE
            mDataBind.mOAuthLoginIv.visibility = View.GONE
            mViewModel.getAccessToken(code, state).observeKt {
                Settings.Account.token = it.access_token
                getUserInfo()
            }
        }
    }

    private fun getUserInfo() {
        mViewModel.getUser().observeKt {
            saveUserInfo(it)
        }
    }

    private fun saveUserInfo(userModel: UserModel) {
        User(userModel.id, userModel.login, userModel.avatar_url).apply {
            Settings.Account.loginUser = this.login
            mViewModel.saveLocalUser(this@apply)
            startActivity<HomeActivity>()
        }
    }

    override fun handleError() {
        mDataBind.mSignInBt.visibility = View.VISIBLE
        mDataBind.mProgressBar.visibility = View.GONE
        mDataBind.mOAuthLoginIv.visibility = View.VISIBLE
        mDataBind.mOAuthProgress.visibility = View.GONE
    }
}