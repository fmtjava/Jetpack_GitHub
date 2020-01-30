package com.fmt.github.user.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class UserInfoPagerAdapter(
    fm: FragmentManager,
    private val mFragmentList: List<Fragment>,
    behavior: Int
) : FragmentStatePagerAdapter(fm, behavior) {//Behavior实现Fragment的懒加载

    private val mTitles = listOf("Info", "Repos", "Favor")

    override fun getItem(position: Int): Fragment = mFragmentList[position]

    override fun getCount(): Int = mFragmentList.size

    override fun getPageTitle(position: Int): CharSequence = mTitles[position]
}