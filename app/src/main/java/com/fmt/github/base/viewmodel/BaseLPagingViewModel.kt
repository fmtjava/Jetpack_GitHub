package com.fmt.github.base.viewmodel

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.fmt.github.config.Configs

/**
 * 基于Paging封装通用ViewModel
 */

abstract class BaseLPagingViewModel<M : Any> : BaseViewModel() {

    val pagedList by lazy {
        Pager(config = PagingConfig(pageSize = Configs.PAGE_SIZE, prefetchDistance = 1)) {
            PageDataSource()
        }.flow.asLiveData().cachedIn(viewModelScope)
    }

    inner class PageDataSource : PagingSource<Int, M>() {
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, M> {
            return try {
                val page = params.key ?: 1
                val list = getDataList(page)
                LoadResult.Page(
                    data = list,
                    prevKey = null,
                    nextKey = if (list.isEmpty()) null else page + 1
                )
            } catch (e: Exception) {
                mStateLiveData.value = ErrorState(e.message)
                LoadResult.Error(e)
            }
        }
    }

    abstract suspend fun getDataList(page: Int): List<M>

}