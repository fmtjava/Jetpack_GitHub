package com.fmt.github.user.fragment

import android.content.Intent
import android.os.Bundle
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.fmt.github.BR
import com.fmt.github.R
import com.fmt.github.base.fragment.BaseVMFragment
import com.fmt.github.constant.Constant
import com.fmt.github.databinding.LayoutReposBinding
import com.fmt.github.home.event.ReposStarEvent
import com.fmt.github.repos.activity.ReposDetailActivity
import com.fmt.github.repos.model.ReposItemModel
import com.fmt.github.user.viewmodel.UserViewModel
import com.fmt.github.util.of
import com.github.nitrico.lastadapter.LastAdapter
import com.github.nitrico.lastadapter.Type
import com.jeremyliao.liveeventbus.LiveEventBus
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.common_refresh_recyclerview.*

class UserReposFragment : BaseVMFragment<UserViewModel>(), OnRefreshListener, OnLoadMoreListener {

    override fun getLayoutRes(): Int = R.layout.common_refresh_recyclerview

    override fun initViewModel(): UserViewModel = of(mActivity, UserViewModel::class.java)

    private val mReposList = ObservableArrayList<ReposItemModel>()

    private var mPage = 1

    var mUserName: String = ""

    var mIsFavor: Boolean = false

    companion object {

        const val KEY = "key"
        const val IS_FAVOR = "is_favor"

        fun newInstance(userName: String, isFavor: Boolean = false): UserReposFragment {
            val reposFragment = UserReposFragment()
            val arguments = Bundle()
            arguments.putString(KEY, userName)
            arguments.putBoolean(IS_FAVOR, isFavor)
            reposFragment.arguments = arguments
            return reposFragment
        }
    }

    override fun initView() {
        initRefreshLayout()
        initRecyclerView()
    }

    private fun initRefreshLayout() {
        mRefreshLayout.setOnRefreshListener(this)
        mRefreshLayout.setOnLoadMoreListener(this)
    }

    private fun initRecyclerView() {
        val type = Type<LayoutReposBinding>(R.layout.layout_repos)
            .onClick {
                go2ReposDetailActivity(mReposList[it.adapterPosition])
            }
        LastAdapter(mReposList, BR.item)
            .map<ReposItemModel>(type)
            .into(mRecyclerView.apply {
                layoutManager = LinearLayoutManager(mActivity)
            })
    }

    override fun initData() {
        arguments?.let {
            mUserName = it.getString(KEY)
            mIsFavor = it.getBoolean(IS_FAVOR)
            initReposViewModelAction()
            if (mIsFavor) initStarEvent()
        }
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mPage = 1
        initReposViewModelAction()
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        mPage++
        initReposViewModelAction()
    }

    private fun initReposViewModelAction() {
        if (mPage == 1) mRefreshLayout.autoRefreshAnimationOnly()
        if (mIsFavor) {
            mViewModel.getStarredRepos(mUserName, mPage).observe(this, mReposListObserver)
        } else {
            mViewModel.getUserPublicRepos(mUserName, mPage).observe(this, mReposListObserver)
        }
    }

    private val mReposListObserver = Observer<List<ReposItemModel>> {
        if (mPage == 1) {
            mReposList.clear()
            mReposList.addAll(it)
            mRefreshLayout.finishRefresh()
        } else {
            mReposList.addAll(it)
            mRefreshLayout.finishLoadMore()
        }
    }

    private fun go2ReposDetailActivity(reposItemModel: ReposItemModel) {
        with(Intent(mActivity, ReposDetailActivity::class.java)) {
            putExtra(ReposDetailActivity.WEB_URL, reposItemModel.html_url)
            putExtra(ReposDetailActivity.REPO, reposItemModel.name)
            putExtra(ReposDetailActivity.OWNER, reposItemModel.owner.login)
        }.run {
            startActivity(this)
        }
    }

    //LiveEventBus实现Android消息总线
    private fun initStarEvent() {
        LiveEventBus.get()
            .with(Constant.STAR_EVENT_KEY, ReposStarEvent::class.java)
            .observe(this, Observer {
                onRefresh(mRefreshLayout)
            })
    }

    override fun dismissLoading() {
        mRefreshLayout.finishRefresh()
        mRefreshLayout.finishLoadMore()
    }
}