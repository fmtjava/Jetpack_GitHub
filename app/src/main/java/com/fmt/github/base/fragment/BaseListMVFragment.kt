package com.fmt.github.base.fragment

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.Observer
import com.fmt.github.R
import com.fmt.github.ext.otherwise
import com.fmt.github.ext.yes
import com.kennyc.view.MultiStateView
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.common_refresh_recyclerview.*

/**
 * 分页列表页面封装
 */
abstract class BaseListMVFragment<M> : BaseVMFragment(), OnRefreshListener,
    OnLoadMoreListener {

    protected val mListData = ObservableArrayList<M>()

    protected var mPage = 1

    override fun getLayoutRes(): Int = R.layout.common_refresh_recyclerview

    override fun initView() {
        initRefreshLayout()
        initRecyclerView()
    }

    private fun initRefreshLayout() {
        mRefreshLayout.run {
            setOnRefreshListener(this@BaseListMVFragment)
            setOnLoadMoreListener(this@BaseListMVFragment)
        }
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mPage = 1
        initViewModelAction()
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        mPage++
        initViewModelAction()
    }

    protected fun initViewModelAction() {
        (mPage == 1).yes {
            mRefreshLayout.autoRefreshAnimationOnly()
        }
        getListData()
    }

    protected val mListObserver = Observer<List<M>> {
        (it != null && it.isNotEmpty()).yes {
            mMultipleStatusView.viewState = MultiStateView.ViewState.CONTENT
        }
        (mPage == 1).yes {
            mListData.clear()
            mListData.addAll(it)
            mRefreshLayout.finishRefresh()
        }.otherwise {
            mListData.addAll(it)
            mRefreshLayout.finishLoadMore()
        }
    }

    override fun dismissLoading() {
        mRefreshLayout.run {
            finishRefresh()
            finishLoadMore()
        }
    }

    abstract fun initRecyclerView()

    abstract fun getListData()

}