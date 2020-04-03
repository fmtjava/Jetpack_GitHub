package com.fmt.github.home.fragment

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.fmt.github.BR
import com.fmt.github.R
import com.fmt.github.base.fragment.BaseListMVFragment
import com.fmt.github.base.viewmodel.BaseViewModel
import com.fmt.github.databinding.LayoutReceivedEventBinding
import com.fmt.github.home.model.ReceivedEventModel
import com.fmt.github.home.viewmodel.HomeViewModel
import com.fmt.github.repos.activity.ReposDetailActivity
import com.github.nitrico.lastadapter.LastAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.github.nitrico.lastadapter.Type
import kotlinx.android.synthetic.main.common_refresh_recyclerview.*

class HomeFragment : BaseListMVFragment<ReceivedEventModel>() {

    private var mUserName = ""

    private val mViewModel: HomeViewModel by viewModel()

    override fun getViewModel(): BaseViewModel = mViewModel

    companion object {

        const val KEY = "key"
        const val BASE_WEB_URL = "https://github.com/"

        fun newInstance(userName: String): HomeFragment {
            val reposFragment = HomeFragment()
            val arguments = Bundle()
            arguments.putString(KEY, userName)
            reposFragment.arguments = arguments
            return reposFragment
        }
    }

    override fun initRecyclerView() {
        val type = Type<LayoutReceivedEventBinding>(R.layout.layout_received_event)
            .onClick {
                go2ReposDetailActivity(mListData[it.adapterPosition])
            }
        LastAdapter(mListData, BR.item)
            .map<ReceivedEventModel>(type)
            .into(mRecyclerView.apply {
                layoutManager = LinearLayoutManager(mActivity)
            })
    }

    override fun initData() {
        arguments?.let {
            mUserName = it.getString(KEY)
            initViewModelAction()
        }
    }

    override fun getListData() {
        mViewModel.queryReceivedEvents(mUserName, mPage).observe(this, mListObserver)
    }

    private fun go2ReposDetailActivity(receivedEventModel: ReceivedEventModel) {
        val splitArr = receivedEventModel.repo.name.split("/")
        with(Intent(mActivity, ReposDetailActivity::class.java)) {
            putExtra(ReposDetailActivity.WEB_URL, "${BASE_WEB_URL}${receivedEventModel.repo.name}")
            putExtra(ReposDetailActivity.OWNER, splitArr[0])
            putExtra(ReposDetailActivity.REPO, splitArr[1])
        }.run {
            startActivity(this)
        }
    }
}