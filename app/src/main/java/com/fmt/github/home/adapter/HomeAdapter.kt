package com.fmt.github.home.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fmt.github.databinding.LayoutReceivedEventBinding
import com.fmt.github.home.model.ReceivedEventModel
import com.fmt.github.repos.activity.ReposDetailActivity

const val BASE_WEB_URL = "https://github.com/"

class HomeAdapter(private val mContext: Context) :
    PagedListAdapter<ReceivedEventModel, HomeAdapter.ViewHolder>(object :
        DiffUtil.ItemCallback<ReceivedEventModel>() {
        override fun areItemsTheSame(oldItem: ReceivedEventModel, newItem: ReceivedEventModel) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: ReceivedEventModel,
            newItem: ReceivedEventModel
        ) =
            oldItem == newItem
    }) {

    private val mLayoutInflater: LayoutInflater = LayoutInflater.from(mContext)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutReceivedEventBinding.inflate(mLayoutInflater, parent, false)
        return ViewHolder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { receivedEventModel ->
            holder.bindData(receivedEventModel)
            holder.itemView.setOnClickListener {
                go2ReposDetailActivity(receivedEventModel)
            }
        }
    }

    private fun go2ReposDetailActivity(receivedEventModel: ReceivedEventModel) {
        val splitArr = receivedEventModel.repo.name.split("/")
        with(Intent(mContext, ReposDetailActivity::class.java)) {
            putExtra(ReposDetailActivity.WEB_URL, "${BASE_WEB_URL}${receivedEventModel.repo.name}")
            putExtra(ReposDetailActivity.OWNER, splitArr[0])
            putExtra(ReposDetailActivity.REPO, splitArr[1])
        }.run {
            mContext.startActivity(this)
        }
    }

    class ViewHolder(itemView: View, val binding: LayoutReceivedEventBinding) :
        RecyclerView.ViewHolder(itemView) {

        fun bindData(receivedEventModel: ReceivedEventModel) {
            binding.item = receivedEventModel
        }
    }
}