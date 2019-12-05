package com.fmt.github.repos.fragment

import android.content.Intent
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.fmt.github.AppContext
import com.fmt.github.BR
import com.fmt.github.R
import com.fmt.github.base.fragment.BaseVMFragment
import com.fmt.github.base.viewmodel.BaseViewModel
import com.fmt.github.databinding.LayoutReposBinding
import com.fmt.github.ext.otherwise
import com.fmt.github.ext.yes
import com.fmt.github.repos.activity.ReposDetailActivity
import com.fmt.github.repos.model.ReposItemModel
import com.fmt.github.repos.model.ReposListModel
import com.fmt.github.repos.viewmodel.ReposViewModel
import com.github.nitrico.lastadapter.LastAdapter
import com.github.nitrico.lastadapter.Type
import com.kennyc.view.MultiStateView
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.common_refresh_recyclerview.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReposFragment : BaseVMFragment(), OnRefreshListener, OnLoadMoreListener {

    private val mViewModel: ReposViewModel by viewModel()

    private val mReposList = ObservableArrayList<ReposItemModel>()

    private var mPage = 1//页码

    private var mSearchKey: String = ""//搜索关键字
    private var mSort: String = ""//排序类型
    private var mOrder: String = ""//升序/降序

    override fun getLayoutRes(): Int = R.layout.common_refresh_recyclerview

    override fun initView() {
        initRefreshLayout()
        initRecyclerView()
    }

    override fun getViewModel(): BaseViewModel = mViewModel

    private fun initRefreshLayout() {
        mRefreshLayout.run {
            setOnRefreshListener(this@ReposFragment)
            setOnLoadMoreListener(this@ReposFragment)
        }
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

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mPage = 1
        searchRepos()
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        mPage++
        searchRepos()
    }

    fun searchReposByKey(searchKey: String = "", sort: String, order: String) {//默认参数,兼容搜索操作
        mSearchKey = searchKey
        mSort = sort
        mOrder = order
        mRefreshLayout.autoRefresh()
    }

    private fun searchRepos() {
        mViewModel.searchRepos(mSearchKey, mSort, mOrder, mPage).observe(this, mSearchReposListObserver)
    }

    private val mSearchReposListObserver = Observer<ReposListModel> {
        dealReposList(it.items)
    }

    private fun dealReposList(items: List<ReposItemModel>) {
        (items.isNotEmpty()).yes {
            mMultipleStatusView.viewState = MultiStateView.ViewState.CONTENT
        }.otherwise {
            (mPage == 1).yes {
                mMultipleStatusView.viewState = MultiStateView.ViewState.EMPTY
            }
        }
        (mPage == 1).yes {
            mReposList.clear()
            mReposList.addAll(items)
            mRefreshLayout.finishRefresh()
        }.otherwise {
            mReposList.addAll(items)
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

    override fun dismissLoading() {
        mRefreshLayout.run {
            finishRefresh()
            finishLoadMore()
        }
    }

}