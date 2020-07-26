package com.fmt.github.user.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class UserInfoPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val mFragmentList: List<Fragment>
) : FragmentStateAdapter(fragmentActivity) {//FragmentStateAdapter在Fragment可见时进入onResume,不可间时进入onStart

    override fun getItemCount(): Int = mFragmentList.size

    override fun createFragment(position: Int): Fragment = mFragmentList[position]
}