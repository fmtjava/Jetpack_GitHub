package com.fmt.github.home.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.fmt.github.R

class HomePageAdapter(fm: FragmentManager, private val mContext: Context, private val mFragmentList: List<Fragment>) :
    FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment = mFragmentList[position]

    override fun getCount(): Int = mFragmentList.size

    override fun getPageTitle(position: Int): CharSequence =
        mContext.resources.getStringArray(R.array.Home_Tabs)[position]

}