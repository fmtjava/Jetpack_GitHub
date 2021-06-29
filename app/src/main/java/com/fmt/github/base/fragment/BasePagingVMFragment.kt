package com.fmt.github.base.fragment

import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fmt.github.R
import com.fmt.github.base.viewmodel.BaseLPagingViewModel
import com.fmt.github.home.adapter.PostsLoadStateAdapter
import kotlinx.android.synthetic.main.common_recyclerview.*

/**
 * 基于Paging封装通用分页列表
 */
abstract class BasePagingVMFragment<M : Any, VM : BaseLPagingViewModel<M>, VH : RecyclerView.ViewHolder> :
    BaseVMFragment() {

    private val mAdapter: PagingDataAdapter<M, VH> by lazy { getAdapter() }

    lateinit var mViewModel: VM

    override fun getLayoutRes(): Int = R.layout.common_recyclerview

    override fun initView() {
        mSwipeRefreshLayout.setOnRefreshListener {
            mAdapter.refresh()
        }
        mRecyclerView.adapter =
            mAdapter.withLoadStateFooter(PostsLoadStateAdapter { mAdapter.retry() })
        mRecyclerView.layoutManager = LinearLayoutManager(mActivity)
        mAdapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.Loading -> mSwipeRefreshLayout.isRefreshing = true
                is LoadState.NotLoading -> mSwipeRefreshLayout.isRefreshing = false
                is LoadState.Error -> mSwipeRefreshLayout.isRefreshing = false
            }
        }

        mViewModel = getViewModel() as VM
        afterViewCreated()
    }

    override fun initData() {
        mViewModel.pagedList.observe(this, {
            mAdapter.submitData(lifecycle, it)
        })
    }

    abstract fun afterViewCreated()

    abstract fun getAdapter(): PagingDataAdapter<M, VH>

}