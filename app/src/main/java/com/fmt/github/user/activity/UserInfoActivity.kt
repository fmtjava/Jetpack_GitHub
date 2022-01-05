package com.fmt.github.user.activity

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import com.fmt.github.R
import com.fmt.github.base.activity.BaseDataBindActivity
import com.fmt.github.databinding.ActivityUserInfoBinding
import com.fmt.github.user.adapter.UserInfoPagerAdapter
import com.fmt.github.user.fragment.UserInfoFragment
import com.fmt.github.user.fragment.UserReposFragment
import com.fmt.github.user.model.UserModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_user_info.*

class UserInfoActivity : BaseDataBindActivity<ActivityUserInfoBinding>() {

    private val mTitles = listOf("Info", "Repos", "Favor")
    lateinit var mUserName:String
    private lateinit var mUserAvatar:String

    companion object {
        const val USER_NAME = "user_name"
        const val USER_AVATAR = "user_avatar"
    }

    override fun getLayoutId(): Int = R.layout.activity_user_info

    override fun initView() {
        initUserInfo()
        initToolbar()
        initViewPager()
    }

    private fun initUserInfo() {
        mUserName = intent.getStringExtra(USER_NAME)!!
        mUserAvatar = intent.getStringExtra(USER_AVATAR)!!

        mDataBind.userName = mUserName
        mDataBind.userAvatar = mUserAvatar
        mUserIconIv.setOnClickListener {
            PhotoPreviewActivity.go2PhotoPreviewActivity(this, mUserAvatar)
        }
    }

    private fun initToolbar() {
        mToolbar.setNavigationOnClickListener {
            supportFinishAfterTransition()//支持转场过渡
        }
    }

    private fun initViewPager() {
        mutableListOf<Fragment>().apply {
            add(UserInfoFragment())
            add(UserReposFragment.newInstance(mUserName))
            add(UserReposFragment.newInstance(mUserName, true))
        }.also { fragmentList ->
            mViewPager.adapter = UserInfoPagerAdapter(this, fragmentList)
        }
        //绑定TabLayout和ViewPager2
        TabLayoutMediator(
            mTabLayout, mViewPager
        ) { tab, position ->
            tab.text = mTitles[position]
        }.attach()
    }

    override fun onBackPressed() {
        supportFinishAfterTransition()//共享元素共享动画
    }
}

fun go2UserInfoActivity(activity: Activity, view: View, userName: String,userAvatar:String) {
    with(Intent(activity, UserInfoActivity::class.java)) {
        putExtra(UserInfoActivity.USER_NAME, userName)
        putExtra(UserInfoActivity.USER_AVATAR, userAvatar)
    }.run {
        //共享元素共享动画
        ActivityOptionsCompat.makeSceneTransitionAnimation(
            activity,
            view,
            "image"
        ).toBundle()
            .also {
                activity.startActivity(this, it)
            }
    }
}