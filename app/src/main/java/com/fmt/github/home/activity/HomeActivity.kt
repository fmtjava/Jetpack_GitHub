package com.fmt.github.home.activity

import android.content.Intent
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.fmt.github.R
import com.fmt.github.base.activity.BaseVMActivity
import com.fmt.github.constant.Constant
import com.fmt.github.data.db.UserDaoImpl
import com.fmt.github.data.storage.Preference
import com.fmt.github.databinding.LayoutNavHeaderBinding
import com.fmt.github.ext.of
import com.fmt.github.home.adapter.HomePageAdapter
import com.fmt.github.home.viewmodel.HomeViewModel
import com.fmt.github.user.activity.AboutActivity
import com.fmt.github.user.activity.LoginActivity
import com.fmt.github.user.activity.UserInfoActivity
import com.fmt.github.user.db.User
import com.fmt.github.user.fragment.UserReposFragment
import com.fmt.github.user.model.UserModel
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch

class HomeActivity : BaseVMActivity<HomeViewModel>(), NavigationView.OnNavigationItemSelectedListener {

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initViewModel(): HomeViewModel = of(this, HomeViewModel::class.java)

    private lateinit var mUser: User

    override fun initView() {
        setSupportActionBar(mToolbar)
        initUserInfo()
        initNavigationView()
        initDrawerLayout()
    }

    private fun initUserInfo() {
        launch {
            mUser = UserDaoImpl.getAll()[0]
            initHeaderLayout()
            initViewPager()
        }
    }

    private fun initHeaderLayout() {
        val dataBind = DataBindingUtil.inflate<LayoutNavHeaderBinding>(
            LayoutInflater.from(this),
            R.layout.layout_nav_header,
            mNavigationView,
            false
        )
        dataBind.user = mUser
        mNavigationView.addHeaderView(dataBind.root)
    }

    private fun initNavigationView() {
        mToolbar.overflowIcon = ContextCompat.getDrawable(this, R.mipmap.icon_search)
        mNavigationView.setNavigationItemSelectedListener(this)
    }

    /**
     * 整合DrawerLayout和Toolbar
     */
    private fun initDrawerLayout() {
        ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close)
            .apply {
                syncState()
            }.run {
                mDrawerLayout.addDrawerListener(this)
            }
    }

    private fun initViewPager() {
        val fragmentList = mutableListOf<Fragment>().apply {
            add(UserReposFragment.newInstance(mUser.login))
            add(UserReposFragment.newInstance(mUser.login, true))
        }
        val homePageAdapter = HomePageAdapter(supportFragmentManager, this, fragmentList)
        mViewPager.adapter = homePageAdapter
        mTabLayout.setupWithViewPager(mViewPager)
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.item_user -> go2UserInfoActivity(mUser.login, mUser.avatar_url)

            R.id.item_copy_right -> go2UserInfoActivity(Constant.AUTHOR_NAME, Constant.AUTHOR_AVATAR_URL)

            R.id.item_about -> Intent(this, AboutActivity::class.java).run { startActivity(this) }

            R.id.item_logout -> logout()
        }
        mDrawerLayout.closeDrawers()//选择菜单时，关闭侧滑菜单
        return true
    }

    private fun go2UserInfoActivity(login: String, avatar_url: String) {
        val userModel = UserModel(login, avatar_url)
        with(Intent(this, UserInfoActivity::class.java)) {
            putExtra(UserInfoActivity.USER_INFO, userModel)
        }.run {
            startActivity(this)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)//ToolBar设置菜单按钮
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_search_repos -> go2SearchActivity(true)
            R.id.item_search_users -> go2SearchActivity(false)
        }
        return true
    }

    private fun go2SearchActivity(fromSearchRepos: Boolean) {
        Intent(this, CommonSearchActivity::class.java).run {
            putExtra(CommonSearchActivity.FROM_SEARCH_REPOS, fromSearchRepos)
            startActivity(this)
        }
    }

    private fun logout() {
        mViewModel.deleteAuthorization(mUser.uid).observe(this, Observer {
            if (it) {
                Preference.clear()
                mViewModel.deleteUser()
                Intent(this, LoginActivity::class.java).run {
                    startActivity(this)
                    finish()
                }
            }
        })
    }
}
