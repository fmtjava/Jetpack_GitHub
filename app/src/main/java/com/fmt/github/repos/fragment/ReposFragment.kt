package com.fmt.github.repos.fragment

import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.fmt.github.R
import com.fmt.github.base.fragment.BaseVMFragment
import com.fmt.github.repos.activity.ReposDetailActivity
import com.fmt.github.repos.adapter.ReposAdapter
import com.fmt.github.repos.model.ReposItemModel
import com.fmt.github.repos.model.ReposListModel
import com.fmt.github.repos.viewmodel.ReposViewModel
import kotlinx.android.synthetic.main.common_recyclerview.*

class ReposFragment : BaseVMFragment<ReposViewModel>(), SwipeRefreshLayout.OnRefreshListener,
    BaseQuickAdapter.RequestLoadMoreListener,
    BaseQuickAdapter.OnItemClickListener {

    override fun getLayoutRes(): Int = R.layout.common_recyclerview

    override fun initViewModel(): ReposViewModel = get(ReposViewModel::class.java)

    private val mReposAdapter by lazy { ReposAdapter() }

    private var mPage = 1//页码

    private var mSearchKey: String = ""//搜索关键字

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
            onItemClickListener = this@ReposFragment
            setOnLoadMoreListener(this@ReposFragment, mRecyclerView)
            mRecyclerView
        }.apply {
            layoutManager = LinearLayoutManager(mActivity)
            adapter = mReposAdapter.apply { setEmptyView(R.layout.layout_empty) }
        }
    }

    override fun onRefresh() {
        mPage = 1
        searchRepos()
    }

    override fun onLoadMoreRequested() {
        mPage++
        searchRepos()
    }

    fun searchReposByKey(searchKey: String = "") {//默认参数,兼容搜索操作
        if (!searchKey.isNullOrEmpty()) {
            mSearchKey = searchKey
            mPage = 1
        }
        searchRepos()
    }

    private fun searchRepos() {
        if (mPage == 1) mSwipeRefreshLayout.isRefreshing = true
        mViewModel.searchRepos(mSearchKey, mPage).observe(this, mSearchReposListObserver)
    }

    private val mSearchReposListObserver = Observer<ReposListModel> {
        dealReposList(it.items)
    }

    private fun dealReposList(items: List<ReposItemModel>) {
        if (mPage == 1) {
            mSwipeRefreshLayout.isRefreshing = false
            mReposAdapter.setNewData(items)
        } else {
            mReposAdapter.addData(items)
        }
        if (items == null || items.isEmpty()) {
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

    override fun handleError() {
        mSwipeRefreshLayout.isRefreshing = false
    }
}