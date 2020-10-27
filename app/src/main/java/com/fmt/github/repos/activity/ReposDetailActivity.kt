package com.fmt.github.repos.activity

import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.fmt.github.R
import com.fmt.github.base.activity.BaseVMActivity
import com.fmt.github.base.viewmodel.BaseViewModel
import com.fmt.github.constant.Constant
import com.fmt.github.ext.*
import com.fmt.github.ext.startActivity
import com.fmt.github.home.event.ReposStarEvent
import com.fmt.github.repos.delegate.AgentWebContainer
import com.fmt.github.repos.delegate.WebDelegate
import com.fmt.github.repos.viewmodel.ReposViewModel
import com.fmt.github.utils.ShareUtils
import com.jeremyliao.liveeventbus.LiveEventBus
import com.like.LikeButton
import com.like.OnLikeListener
import kotlinx.android.synthetic.main.activity_repos_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReposDetailActivity : BaseVMActivity() {

    private val mViewModel: ReposViewModel by viewModel()
    private lateinit var mWebDelegate: WebDelegate
    private lateinit var mOwner: String
    private lateinit var mRepos: String
    private lateinit var mWebUrl: String

    override fun getLayoutId(): Int = R.layout.activity_repos_detail

    companion object {
        const val WEB_URL = "web_url"
        const val OWNER = "owner"
        const val REPO = "repo"
    }

    override fun initView() {
        mOwner = intent.getStringExtra(OWNER)
        mRepos = intent.getStringExtra(REPO)
        mWebUrl = intent.getStringExtra(WEB_URL)
        mWebDelegate = WebDelegate.create(AgentWebContainer(), this, mRootView, mWebUrl)
        lifecycle.addObserver(mWebDelegate)
        setSupportActionBar(mToolbar)
        supportActionBar?.let { actionBar ->
            actionBar.setDisplayHomeAsUpEnabled(true)//添加默认的返回图标
            actionBar.setHomeButtonEnabled(true)//设置返回键可用
            actionBar.title = mRepos//设置标题
        }
        initListener()
    }

    override fun getViewModel(): BaseViewModel = mViewModel

    private fun initListener() {
        mToolbar.setNavigationOnClickListener {
            mWebDelegate.back().no {
                finish()
            }
        }
        mFavorIb.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton) {
                starRepo()
            }

            override fun unLiked(likeButton: LikeButton) {
                unStarRepo()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_repos_detail_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_browser_open -> ShareUtils.openUrl(this, mWebUrl)
            R.id.item_copy_link -> ShareUtils.copyClipboard(this, mWebUrl)
            R.id.item_share -> ShareUtils.shareText(this, mWebUrl)
        }
        return true
    }

    override fun initData() {
        checkRepoStarred()
    }

    private fun checkRepoStarred() {
        mViewModel.checkRepoStarred(mOwner, mRepos)
            .observe(this, {
                fl_favor.visibility = View.VISIBLE
                mFavorIb.isLiked = it
            })
    }

    private fun starRepo() {
        mViewModel.starRepo(mOwner, mRepos)
            .observe(this@ReposDetailActivity, {
                successToast(getString(R.string.stared))
                LiveEventBus.get().with(Constant.STAR_EVENT_KEY).post(ReposStarEvent())
            })
    }

    private fun unStarRepo() {
        mViewModel.unStarRepo(mOwner, mRepos)
            .observe(this@ReposDetailActivity, {
                successToast(getString(R.string.un_stared))
                LiveEventBus.get().with(Constant.STAR_EVENT_KEY).post(ReposStarEvent())
            })
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean =
        (mWebDelegate.handleKeyEvent(keyCode, event)).yes {
            true
        }.otherwise {
            super.onKeyDown(keyCode, event)
        }
}

fun go2ReposDetailActivity(activity: Activity, webUrl: String, repo: String, owner: String) {
    Bundle().run {
        putString(ReposDetailActivity.WEB_URL, webUrl)
        putString(ReposDetailActivity.REPO, repo)
        putString(ReposDetailActivity.OWNER, owner)
        activity.startActivity<ReposDetailActivity>(this)
    }
}