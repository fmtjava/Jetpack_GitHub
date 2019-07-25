package com.fmt.github.user.fragment

import androidx.lifecycle.Observer
import com.fmt.github.R
import com.fmt.github.base.fragment.BaseVMFragment
import com.fmt.github.user.activity.UserInfoActivity
import com.fmt.github.user.model.UserInfoModel
import com.fmt.github.user.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_user_info.*

class UserInfoFragment : BaseVMFragment<UserViewModel>() {

    override fun getLayoutRes(): Int = R.layout.fragment_user_info

    override fun initViewModel(): UserViewModel = get(UserViewModel::class.java)

    lateinit var mUser: String

    override fun initView() {
        mUser = (activity as UserInfoActivity).mUserModel.login
        mSwipeRefreshLayout.setOnRefreshListener {
            initData()
        }
    }

    override fun initData() {
        mSwipeRefreshLayout.isRefreshing = true
        mViewModel.getUserInfo(mUser).observe(this, Observer<UserInfoModel> {
            mSwipeRefreshLayout.isRefreshing = false
            mUserNameTv.text = it.login
            mDescTv.text = it.bio
            mEmailTv.text = it.email
            mBlogTv.text = it.blog
            mFollowersTv.text = it.followers.toString()
            mFollowingTv.text = it.following.toString()
            mRepositoriesTv.text = it.public_repos.toString()
            mGistsTv.text = it.public_gists.toString()
        })
    }

    override fun handleError() {
        mSwipeRefreshLayout.isRefreshing = false
    }

}