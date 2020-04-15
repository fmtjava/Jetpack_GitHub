package com.fmt.github.home.fragment

import android.os.Bundle
import androidx.paging.PagedListAdapter
import com.fmt.github.base.fragment.BasePagingVMFragment
import com.fmt.github.base.viewmodel.BaseViewModel
import com.fmt.github.home.adapter.HomeAdapter
import com.fmt.github.home.model.ReceivedEventModel
import com.fmt.github.home.viewmodel.ReceivedEventViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReceivedEventFragment :
    BasePagingVMFragment<ReceivedEventModel, ReceivedEventViewModel, HomeAdapter.ViewHolder>() {

    private var mUserName = ""

    private val mReceivedEventViewModel: ReceivedEventViewModel by viewModel()

    override fun getViewModel(): BaseViewModel = mReceivedEventViewModel

    companion object {

        const val KEY = "key"

        fun newInstance(userName: String): ReceivedEventFragment {
            val reposFragment = ReceivedEventFragment()
            val arguments = Bundle()
            arguments.putString(KEY, userName)
            reposFragment.arguments = arguments
            return reposFragment
        }
    }

    override fun afterViewCreated() {
        arguments?.let {
            mUserName = it.getString(KEY)
            mViewModel.user = mUserName
        }
    }

    override fun getAdapter(): PagedListAdapter<ReceivedEventModel, HomeAdapter.ViewHolder> =
        HomeAdapter(mActivity)
}