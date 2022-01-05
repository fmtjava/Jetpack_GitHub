package com.fmt.github.user.fragment

import com.fmt.github.R
import com.fmt.github.base.fragment.BaseDataBindVMFragment
import com.fmt.github.base.viewmodel.BaseViewModel
import com.fmt.github.databinding.FragmentUserInfoBinding
import com.fmt.github.user.activity.UserInfoActivity
import com.fmt.github.user.viewmodel.UserViewModel
import com.fmt.github.utils.FOLLOW_PAGE
import com.fmt.github.utils.NavigationUtil
import kotlinx.android.synthetic.main.fragment_user_info.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserInfoFragment : BaseDataBindVMFragment<FragmentUserInfoBinding>() {

    private val FOLLOWERS_TYPE = "followers"
    private val FOLLOWING_TYPE = "following"
    private val USERNAME_KEY = "userName"
    private val TYPE_KEY = "type"

    private val mViewModel: UserViewModel by viewModel()

    override fun getViewModel(): BaseViewModel = mViewModel

    override fun getLayoutRes(): Int = R.layout.fragment_user_info

    private lateinit var mUser: String

    override fun initView() {
        mUser = (activity as UserInfoActivity).mUserName
        mSwipeRefreshLayout.setOnRefreshListener {
            initData()
        }
        mDataBind.llFollowers.setOnClickListener {
            go2FollowFlutterPage(FOLLOWERS_TYPE)
        }
        mDataBind.llFollowing.setOnClickListener {
            go2FollowFlutterPage(FOLLOWING_TYPE)
        }
    }

    private fun go2FollowFlutterPage(type: String) {
        NavigationUtil.go(FOLLOW_PAGE,mapOf(USERNAME_KEY to mUser, TYPE_KEY to type))
    }

    override fun showLoading() {
        mSwipeRefreshLayout.isRefreshing = true
    }

    override fun dismissLoading() {
        mSwipeRefreshLayout.isRefreshing = false
    }

    override fun initData() {
        mDataBind.userViewModel = mViewModel
        mViewModel.getUserInfo(mUser)
    }
}