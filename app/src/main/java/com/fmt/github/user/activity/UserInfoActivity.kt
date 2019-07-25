package com.fmt.github.user.activity

import androidx.fragment.app.Fragment
import com.fmt.github.R
import com.fmt.github.base.activity.BaseActivity
import com.fmt.github.ext.loadUrl
import com.fmt.github.user.adapter.UserInfoPagerAdapter
import com.fmt.github.user.fragment.UserInfoFragment
import com.fmt.github.user.fragment.UserReposFragment
import com.fmt.github.user.model.UserModel
import kotlinx.android.synthetic.main.activity_user_info.*

class UserInfoActivity : BaseActivity() {

    lateinit var mUserModel: UserModel

    companion object {
        const val USER_INFO = "user_info"
    }

    override fun getLayoutId(): Int = R.layout.activity_user_info

    override fun initView() {
        initUserInfo()
        initToolbar()
        initViewPager()
    }

    private fun initUserInfo() {
        mUserModel = intent.getSerializableExtra(USER_INFO) as UserModel
        mUserIconIv.loadUrl(mUserModel.avatar_url)
        mUserNameTv.text = mUserModel.login
        mIntroduceTv.text = String.format(getString(R.string.introduce), mUserModel.login)
    }

    private fun initToolbar() {
        mToolbar.setNavigationOnClickListener {
            supportFinishAfterTransition()//支持转场过渡
        }
    }

    private fun initViewPager() {
        val fragmentList = mutableListOf<Fragment>().apply {
            add(UserInfoFragment())
            add(UserReposFragment.newInstance(mUserModel.login))
            add(UserReposFragment.newInstance(mUserModel.login, true))
        }
        mViewPager.adapter = UserInfoPagerAdapter(supportFragmentManager, fragmentList)
        mTabLayout.setupWithViewPager(mViewPager)
    }

    override fun onBackPressed() {
        supportFinishAfterTransition()
    }
}