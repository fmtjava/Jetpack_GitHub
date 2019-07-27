package com.fmt.github.user.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.fmt.github.R
import com.fmt.github.base.fragment.BaseVMFragment
import com.fmt.github.constant.Constant
import com.fmt.github.home.event.ReposStarEvent
import com.fmt.github.repos.activity.ReposDetailActivity
import com.fmt.github.repos.adapter.ReposAdapter
import com.fmt.github.repos.model.ReposItemModel
import com.fmt.github.user.viewmodel.UserViewModel
import com.jeremyliao.liveeventbus.LiveEventBus
import kotlinx.android.synthetic.main.common_recyclerview.*

class UserReposFragment : BaseVMFragment<UserViewModel>(), SwipeRefreshLayout.OnRefreshListener,
    BaseQuickAdapter.RequestLoadMoreListener,
    BaseQuickAdapter.OnItemClickListener {

    override fun getLayoutRes(): Int = R.layout.common_recyclerview

    override fun initViewModel(): UserViewModel = get(UserViewModel::class.java)

    private val mReposAdapter by lazy { ReposAdapter() }

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
        initSwipeRefreshLayout()
        initRecyclerView()
    }

    private fun initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(this)
    }

    private fun initRecyclerView() {
        with(mReposAdapter) {
            disableLoadMoreIfNotFullPage(mRecyclerView)
            onItemClickListener = this@UserReposFragment
            setOnLoadMoreListener(this@UserReposFragment, mRecyclerView)
            openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM)
            mRecyclerView
        }.apply {
            layoutManager = LinearLayoutManager(mActivity)
            adapter = mReposAdapter.apply { setEmptyView(R.layout.layout_empty) }
        }
    }

    override fun initData() {
        arguments?.let {
            mUserName = it.getString(KEY)
            mIsFavor = it.getBoolean(IS_FAVOR)
            initReposViewModelAction()
            if (mIsFavor) initStarEvent()
        }
    }

    override fun onRefresh() {
        mPage = 1
        initReposViewModelAction()
    }

    override fun onLoadMoreRequested() {
        mPage++
        initReposViewModelAction()
    }

    private fun initReposViewModelAction() {
        if (mPage == 1) mSwipeRefreshLayout.isRefreshing = true
        if (mIsFavor) {
            mViewModel.getStarredRepos(mUserName, mPage).observe(this, mReposListObserver)
        } else {
            mViewModel.getUserPublicRepos(mUserName, mPage).observe(this, mReposListObserver)
        }
    }

    private val mReposListObserver = Observer<List<ReposItemModel>> {
        if (mPage == 1) {
            mSwipeRefreshLayout.isRefreshing = false
            mReposAdapter.setNewData(it)
        } else {
            mReposAdapter.addData(it)
        }
        if (it == null || it.isEmpty()) {
            mReposAdapter.loadMoreEnd(true)
        } else {
            mReposAdapter.loadMoreComplete()
        }
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        with(Intent(mActivity, ReposDetailActivity::class.java)) {
            putExtra(ReposDetailActivity.WEB_URL, mReposAdapter.data[position].html_url)
            putExtra(ReposDetailActivity.REPO, mReposAdapter.data[position].name)
            putExtra(ReposDetailActivity.OWNER, mReposAdapter.data[position].owner.login)
        }.run {
            startActivity(this)
        }
    }

    //LiveEventBus实现Android消息总线
    private fun initStarEvent() {
        LiveEventBus.get()
            .with(Constant.STAR_EVENT_KEY, ReposStarEvent::class.java)
            .observe(this, Observer {
                onRefresh()
            })
    }

    override fun handleError() {
        mSwipeRefreshLayout.isRefreshing = false
    }
}