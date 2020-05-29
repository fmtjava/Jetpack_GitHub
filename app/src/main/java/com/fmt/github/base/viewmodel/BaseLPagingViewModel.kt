package com.fmt.github.base.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.fmt.github.config.Configs
import kotlinx.coroutines.launch

/**
 * 基于Paging封装通用ViewModel
 */
abstract class BaseLPagingViewModel<M> : BaseViewModel() {

    private lateinit var mDataSource: PageKeyedDataSource<Int, M>

    val mBoundaryData = MutableLiveData(false)//控制页面显示状态

    val refreshState = MutableLiveData(false)

    val loadMoreState = MutableLiveData(false)

    private var loadMoreRetry: (() -> Unit)? = null

    val pagedList: LiveData<PagedList<M>> by lazy {
        LivePagedListBuilder<Int, M>(
            object : DataSource.Factory<Int, M>() {
                override fun create(): DataSource<Int, M> {
                    mDataSource = PageDataSource()
                    return mDataSource
                }
            }, PagedList.Config.Builder()
                .setPageSize(Configs.PAGE_SIZE)
                .setInitialLoadSizeHint(12)
                .build()
        ).build()
    }

    //真正加载数据的来源
    inner class PageDataSource : PageKeyedDataSource<Int, M>() {
        override fun loadInitial(
            params: LoadInitialParams<Int>,
            callback: LoadInitialCallback<Int, M>
        ) {
            viewModelScope.launch {
                try {
                    val list = getDataList(1)
                    mBoundaryData.postValue(list.isNotEmpty())
                    callback.onResult(list, null, 2)
                    mStateLiveData.value = SuccessState
                    refreshState.postValue(false)
                } catch (e: Exception) {
                    refreshState.postValue(true)
                    mStateLiveData.value = ErrorState(e.message)
                }
            }
        }

        override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, M>) {
            viewModelScope.launch {
                try {
                    val list = getDataList(params.key)
                    callback.onResult(
                        list,
                        params.key + 1
                    )
                    mStateLiveData.value = SuccessState
                    loadMoreState.postValue(false)
                } catch (e: Exception) {
                    mStateLiveData.value = ErrorState(e.message)
                    loadMoreState.postValue(true)
                    loadMoreRetry = {
                        //保存加载更多失败时的场景,防止第一次加载失败后，后续无法再次调用loadAfter
                        loadMoreFail(params, callback)
                    }
                }
            }
        }

        override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, M>) {}
    }

    fun refresh() {//Paging刷新数据
        mDataSource.invalidate()
    }

    fun loadMoreRetry() {//加载更多失败重试
        loadMoreRetry?.invoke()
    }

    private fun loadMoreFail(//加载更多失败时调用
        params: PageKeyedDataSource.LoadParams<Int>,
        callback: PageKeyedDataSource.LoadCallback<Int, M>
    ) {
        mDataSource.loadAfter(params, callback)
    }

    abstract suspend fun getDataList(page: Int): List<M>


}