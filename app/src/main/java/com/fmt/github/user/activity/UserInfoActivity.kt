package com.fmt.github.user.activity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.fmt.github.R
import com.fmt.github.base.activity.BaseDataBindActivity
import com.fmt.github.databinding.ActivityUserInfoBinding
import com.fmt.github.user.adapter.UserInfoPagerAdapter
import com.fmt.github.user.fragment.UserInfoFragment
import com.fmt.github.user.fragment.UserReposFragment
import com.fmt.github.user.model.UserModel
import kotlinx.android.synthetic.main.activity_user_info.*

class UserInfoActivity : BaseDataBindActivity<ActivityUserInfoBinding>() {

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
        mDataBind.userModel = mUserModel
    }

    private fun initToolbar() {
        mToolbar.setNavigationOnClickListener {
            supportFinishAfterTransition()//支持转场过渡
        }
    }

    private fun initViewPager() {
        mutableListOf<Fragment>().apply {
            add(UserInfoFragment())
            add(UserReposFragment.newInstance(mUserModel.login))
            add(UserReposFragment.newInstance(mUserModel.login, true))
        }.apply {
            mViewPager.adapter = UserInfoPagerAdapter(supportFragmentManager, this,
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
            mTabLayout.setupWithViewPager(mViewPager)
        }
    }

    override fun onBackPressed() {
        supportFinishAfterTransition()//共享元素共享动画
    }
}