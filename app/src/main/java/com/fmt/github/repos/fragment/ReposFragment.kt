package com.fmt.github.repos.fragment

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.fmt.github.BR
import com.fmt.github.R
import com.fmt.github.base.fragment.BaseListMVFragment
import com.fmt.github.base.viewmodel.BaseViewModel
import com.fmt.github.databinding.LayoutReposBinding
import com.fmt.github.repos.activity.ReposDetailActivity
import com.fmt.github.repos.model.ReposItemModel
import com.fmt.github.repos.viewmodel.ReposViewModel
import com.github.nitrico.lastadapter.LastAdapter
import com.github.nitrico.lastadapter.Type
import kotlinx.android.synthetic.main.common_refresh_recyclerview.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReposFragment : BaseListMVFragment<ReposItemModel>(){

    private val mViewModel: ReposViewModel by viewModel()
    private var mSearchKey: String = ""//搜索关键字
    private var mSort: String = ""//排序类型
    private var mOrder: String = ""//升序/降序

    override fun getViewModel(): BaseViewModel = mViewModel

    override fun initRecyclerView() {
        val type = Type<LayoutReposBinding>(R.layout.layout_repos)
            .onClick {
                go2ReposDetailActivity(mListData[it.adapterPosition])
            }
        LastAdapter(mListData, BR.item)
            .map<ReposItemModel>(type)
            .into(mRecyclerView.apply {
                layoutManager = LinearLayoutManager(mActivity)
            })
    }

    override fun getListData() {
        mViewModel.searchRepos(mSearchKey, mSort, mOrder, mPage).observe(this, mListObserver)
    }

    fun searchReposByKey(searchKey: String = "", sort: String, order: String) {//默认参数,兼容搜索操作
        mSearchKey = searchKey
        mSort = sort
        mOrder = order
        mRefreshLayout.autoRefresh()
    }

    private fun go2ReposDetailActivity(reposItemModel: ReposItemModel) {
        with(Intent(mActivity, ReposDetailActivity::class.java)) {
            putExtra(ReposDetailActivity.WEB_URL, reposItemModel.html_url)
            putExtra(ReposDetailActivity.REPO, reposItemModel.name)
            putExtra(ReposDetailActivity.OWNER, reposItemModel.owner.login)
        }.run {
            startActivity(this)
        }
    }
}