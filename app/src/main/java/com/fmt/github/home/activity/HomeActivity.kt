package com.fmt.github.home.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.fmt.github.FlutterAppActivity
import com.fmt.github.R
import com.fmt.github.base.activity.BaseVMActivity
import com.fmt.github.base.viewmodel.BaseViewModel
import com.fmt.github.constant.Constant
import com.fmt.github.data.storage.Preference
import com.fmt.github.databinding.LayoutNavHeaderBinding
import com.fmt.github.ext.getVersionName
import com.fmt.github.ext.showConfirmPopup
import com.fmt.github.ext.startActivity
import com.fmt.github.ext.yes
import com.fmt.github.home.viewmodel.HomeViewModel
import com.fmt.github.home.work.DownLoadWork
import com.fmt.github.user.activity.AboutActivity
import com.fmt.github.user.activity.LoginActivity
import com.fmt.github.user.activity.UserInfoActivity
import com.fmt.github.user.dao.UserDao
import com.fmt.github.user.model.UserModel
import com.fmt.github.user.model.db.User
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : BaseVMActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val mUserDao: UserDao by inject()//kotlin的val相当于Java的final

    private val mViewModel: HomeViewModel by viewModel()

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun getViewModel(): BaseViewModel = mViewModel

    private lateinit var mUser: User

    override fun initView() {
        setSupportActionBar(mToolbar)
        initUserInfo()
        initNavigationView()
        initDrawerLayout()
    }

    override fun initData() {
        checkVersionUpdate()
    }

    private fun initUserInfo() {
        lifecycleScope.launch {
            //Androidx的协程支持LifecycleScope、ViewModelScope
            mUser = mUserDao.getAll()[0]
            initHeaderLayout()
        }
    }

    private fun initHeaderLayout() {
        val dataBind =
            LayoutNavHeaderBinding.inflate(LayoutInflater.from(this), mNavigationView, false)
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

    override fun onSupportNavigateUp(): Boolean =
        Navigation.findNavController(this, R.id.nav_home_fragment).navigateUp()

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.item_user -> go2UserInfoActivity(mUser.login, mUser.avatar_url)

            R.id.item_about -> startActivity<AboutActivity>(false)

            R.id.item_trend -> {
                Bundle().run {
                    putString(FlutterAppActivity.INIT_PARAMS, Constant.Router.ROUTER_TREND)
                    startActivity<FlutterAppActivity>(this)
                }
            }

            R.id.item_logout -> showLogoutPopup()
        }
        mDrawerLayout.closeDrawers()//选择菜单时，关闭侧滑菜单
        return true
    }

    private fun go2UserInfoActivity(login: String, avatar_url: String) {
        Bundle().run {
            val userModel = UserModel(login, avatar_url)
            putSerializable(UserInfoActivity.USER_INFO, userModel)
            startActivity<UserInfoActivity>(this)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_home_toolbar, menu)//ToolBar设置菜单按钮
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {//增强版switch
            R.id.item_search_repos -> go2SearchActivity(true)
            R.id.item_search_users -> go2SearchActivity(false)
        }
        return true
    }

    private fun go2SearchActivity(fromSearchRepos: Boolean) {
        Bundle().run {
            putBoolean(CommonSearchActivity.FROM_SEARCH_REPOS, fromSearchRepos)
            startActivity<CommonSearchActivity>(this)
        }
    }

    private fun checkVersionUpdate() {
        mViewModel.getReleases().observe(this, {
            (it.tag_name != getVersionName()).yes {
                showVersionUpdatePopup(it.assets[0].browser_download_url)
            }
        })
    }

    private fun showVersionUpdatePopup(downLoadUrl: String) {
        lifecycleScope.launch {
            showConfirmPopup(
                this@HomeActivity,
                getString(R.string.new_version_found),
                getString(R.string.update_message)
            ).yes {
                DownLoadWork.startDownLoadWork(this@HomeActivity, downLoadUrl)
            }
        }
    }

    private fun showLogoutPopup() {
        lifecycleScope.launch {
            showConfirmPopup(
                this@HomeActivity,
                getString(R.string.tips),
                getString(R.string.logout_message)
            ).yes {
                logout()
            }
        }
    }

    private fun logout() {
        Preference.clear()
        mViewModel.deleteUser()
        startActivity<LoginActivity>()
    }
}
