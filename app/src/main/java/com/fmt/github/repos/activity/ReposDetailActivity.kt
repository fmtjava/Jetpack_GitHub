package com.fmt.github.repos.activity

import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.lifecycle.Observer
import com.fmt.github.R
import com.fmt.github.base.activity.BaseVMActivity
import com.fmt.github.base.viewmodel.BaseViewModel
import com.fmt.github.constant.Constant
import com.fmt.github.ext.*
import com.fmt.github.ext.startActivity
import com.fmt.github.home.event.ReposStarEvent
import com.fmt.github.repos.delegate.WebDelegate
import com.fmt.github.repos.viewmodel.ReposViewModel
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
        mWebDelegate = WebDelegate.create(this, mRootView, mWebUrl)
        mWebDelegate.onCreate()
        initListener()
    }

    override fun getViewModel(): BaseViewModel = mViewModel

    private fun initListener() {
        mBackIB.setOnClickListener {
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

    override fun initData() {
        mReposNameTv.text = mRepos
        checkRepoStarred()
    }

    private fun checkRepoStarred() {
        mViewModel.checkRepoStarred(mOwner, mRepos)
            .observe(this, Observer {
                mFavorIb.visibility = View.VISIBLE
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

    override fun onPause() {
        mWebDelegate.onPause()
        super.onPause()
    }

    override fun onResume() {
        mWebDelegate.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        mWebDelegate.onDestroy()
        super.onDestroy()
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