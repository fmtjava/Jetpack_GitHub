package com.fmt.github.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fmt.github.R
import kotlinx.android.synthetic.main.layout_network_state.view.*

/**
 * 设置底部加载进度或者加载出错时候的布局
 */
class PostsLoadStateAdapter(
    private val retryCallback: () -> Unit
) : LoadStateAdapter<NetworkStateItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): NetworkStateItemViewHolder = NetworkStateItemViewHolder(parent, retryCallback)

    override fun onBindViewHolder(holder: NetworkStateItemViewHolder, loadState: LoadState) {
        holder.bindTo(loadState)
    }
}

class NetworkStateItemViewHolder(
    parent: ViewGroup,
    private val retryCallback: () -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.layout_network_state, parent, false)
) {

    init {
        itemView.mRetryLL.setOnClickListener {
            retryCallback.invoke()
        }
    }

    fun bindTo(loadState: LoadState) {
        itemView.mProgressBar.isVisible = loadState is LoadState.Loading
        itemView.mRetryLL.isVisible = loadState is LoadState.Error
    }
}

