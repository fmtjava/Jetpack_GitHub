package com.fmt.github.base.fragment

import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fmt.github.R
import com.fmt.github.base.viewmodel.BaseLPagingViewModel
import com.fmt.github.ext.yes
import com.google.android.material.button.MaterialButton
import com.kennyc.view.MultiStateView
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.common_refresh_recyclerview.*

/**
 * 基于Paging封装通用分页列表
 */
abstract class BasePagingVMFragment<M, VM : BaseLPagingViewModel<M>, VH : RecyclerView.ViewHolder> :
    BaseVMFragment(), OnRefreshListener,
    OnLoadMoreListener {

    private val mAdapter: PagedListAdapter<M, VH> by lazy { getAdapter() }

    lateinit var mViewModel: VM

    override fun getLayoutRes(): Int = R.layout.common_refresh_recyclerview

    override fun initView() {
        mMultipleStatusView.viewState = MultiStateView.ViewState.LOADING
        mMultipleStatusView.getView(MultiStateView.ViewState.ERROR)
            ?.findViewById<MaterialButton>(R.id.mb_retry_load)?.setOnClickListener {
                mMultipleStatusView.viewState = MultiStateView.ViewState.LOADING
                mViewModel.refresh()
            }
        mRefreshLayout.run {
            setOnRefreshListener(this@BasePagingVMFragment)
            setOnLoadMoreListener(this@BasePagingVMFragment)
        }

        mRecyclerView.layoutManager = LinearLayoutManager(mActivity)
        mRecyclerView.adapter = mAdapter

        mViewModel = getViewModel() as VM
        mViewModel.mBoundaryData.observe(this, Observer {
            it.yes {
                mMultipleStatusView.viewState = MultiStateView.ViewState.CONTENT
            }
        })
        mViewModel.refreshState.observe(this, Observer {
            it.yes {
                mMultipleStatusView.viewState = MultiStateView.ViewState.ERROR
            }
        })
        mViewModel.loadMoreState.observe(this, Observer {
            //上拉加载进度条只有在Paging加载更多失败时才有效(用于规避Paging加载更多失败后，无法再次加载问题)
            mRefreshLayout.setEnableLoadMore(it)
        })

        afterViewCreated()
    }

    override fun initData() {
        mViewModel.pagedList.observe(this, Observer<PagedList<M>> {
            mAdapter.submitList(it)
        })
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mViewModel.refresh()
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        mViewModel.loadMoreRetry()
    }

    override fun dismissLoading() {
        mRefreshLayout.run {
            finishRefresh()
            finishLoadMore()
        }
    }

    abstract fun afterViewCreated()

    abstract fun getAdapter(): PagedListAdapter<M, VH>

}