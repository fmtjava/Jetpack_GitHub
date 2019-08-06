package com.fmt.github.repos.activity

import android.view.KeyEvent
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.fmt.github.R
import com.fmt.github.base.activity.BaseVMActivity
import com.fmt.github.constant.Constant
import com.fmt.github.ext.successToast
import com.fmt.github.home.event.ReposStarEvent
import com.fmt.github.repos.viewmodel.ReposViewModel
import com.fmt.github.util.of
import com.jeremyliao.liveeventbus.LiveEventBus
import com.just.agentweb.AgentWeb
import com.like.LikeButton
import com.like.OnLikeListener
import kotlinx.android.synthetic.main.activity_repos_detail.*

class ReposDetailActivity : BaseVMActivity<ReposViewModel>() {

    private lateinit var mAgentWeb: AgentWeb
    private lateinit var mOwner: String
    private lateinit var mRepos: String
    private lateinit var mWebUrl: String

    override fun getLayoutId(): Int = R.layout.activity_repos_detail

    override fun initViewModel(): ReposViewModel = of(this,ReposViewModel::class.java)

    companion object {
        const val WEB_URL = "web_url"
        const val OWNER = "owner"
        const val REPO = "repo"
    }

    override fun initView() {
        mOwner = intent.getStringExtra(OWNER)
        mRepos = intent.getStringExtra(REPO)
        mWebUrl = intent.getStringExtra(WEB_URL)
        initAgentWeb()
        initListener()
    }

    private fun initAgentWeb() {
        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(mRootView, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator(ContextCompat.getColor(this, R.color.indicator_color))
            .createAgentWeb()
            .ready()
            .go(mWebUrl)
    }

    private fun initListener() {
        mBackIB.setOnClickListener {
            if (!mAgentWeb.back()) {
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
            .observe(this@ReposDetailActivity, Observer {
                successToast(getString(R.string.stared))
                LiveEventBus.get().with(Constant.STAR_EVENT_KEY).post(ReposStarEvent())
            })
    }

    private fun unStarRepo() {
        mViewModel.unStarRepo(mOwner, mRepos)
            .observe(this@ReposDetailActivity, Observer {
                successToast(getString(R.string.un_stared))
                LiveEventBus.get().with(Constant.STAR_EVENT_KEY).post(ReposStarEvent())
            })
    }

    override fun onPause() {
        mAgentWeb.webLifeCycle.onPause()
        super.onPause()
    }

    override fun onResume() {
        mAgentWeb.webLifeCycle.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        mAgentWeb.webLifeCycle.onDestroy()
        super.onDestroy()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            true
        } else super.onKeyDown(keyCode, event)
    }
}