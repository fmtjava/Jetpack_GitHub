package com.fmt.github.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.fmt.github.AppContext
import com.fmt.github.R

class HomePageAdapter(
    fm: FragmentManager,
    private val mFragmentList: List<Fragment>,
    behavior: Int
) :
    FragmentStatePagerAdapter(fm, behavior) {

    override fun getItem(position: Int): Fragment = mFragmentList[position]

    override fun getCount(): Int = mFragmentList.size

    override fun getPageTitle(position: Int): CharSequence =
        AppContext.resources.getStringArray(R.array.Home_Tabs)[position]

}