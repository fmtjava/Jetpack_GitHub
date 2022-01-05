package com.fmt.github.base.fragment

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fmt.github.R
import com.fmt.github.ext.otherwise
import com.fmt.github.ext.yes
import com.kennyc.view.MultiStateView
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.common_recyclerview.*
import kotlinx.android.synthetic.main.common_refresh_recyclerview.*
import kotlinx.android.synthetic.main.common_refresh_recyclerview.mRecyclerView

/**
 * 分页列表页面封装
 */
abstract class BaseListMVFragment<M> : BaseVMFragment(), OnRefreshListener,
    OnLoadMoreListener {

    protected val mListData = ObservableArrayList<M>()

    protected var mPage = 1

    private var mIsScrolling = false

    override fun getLayoutRes(): Int = R.layout.common_refresh_recyclerview

    override fun initView() {
        initRefreshLayout()
        initScrollListener()
        initRecyclerView()
    }

    //RecyclerView 滑动时图片加载的优化
    private fun initScrollListener() {
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING || newState == RecyclerView.SCROLL_STATE_SETTLING) {
                    mIsScrolling = true
                    Glide.with(this@BaseListMVFragment).pauseRequests()
                } else if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (mIsScrolling) {
                        Glide.with(this@BaseListMVFragment).resumeRequests()
                    }
                    mIsScrolling = false
                }
            }
        })
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