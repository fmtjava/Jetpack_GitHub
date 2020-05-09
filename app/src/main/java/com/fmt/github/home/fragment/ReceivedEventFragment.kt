package com.fmt.github.home.fragment

import androidx.paging.PagedListAdapter
import com.fmt.github.base.fragment.BasePagingVMFragment
import com.fmt.github.base.viewmodel.BaseViewModel
import com.fmt.github.config.Settings
import com.fmt.github.home.adapter.HomeAdapter
import com.fmt.github.home.model.ReceivedEventModel
import com.fmt.github.home.viewmodel.ReceivedEventViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReceivedEventFragment :
    BasePagingVMFragment<ReceivedEventModel, ReceivedEventViewModel, HomeAdapter.ViewHolder>() {

    private val mReceivedEventViewModel: ReceivedEventViewModel by viewModel()

    override fun getViewModel(): BaseViewModel = mReceivedEventViewModel

    override fun afterViewCreated() {
        mViewModel.user = Settings.Account.loginUser
    }

    override fun getAdapter(): PagedListAdapter<ReceivedEventModel, HomeAdapter.ViewHolder> =
        HomeAdapter(mActivity)
}