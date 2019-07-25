package com.fmt.github.user.fragment

import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.fmt.github.R
import com.fmt.github.base.fragment.BaseVMFragment
import com.fmt.github.user.activity.UserInfoActivity
import com.fmt.github.user.adapter.UserAdapter
import com.fmt.github.user.model.UserListModel
import com.fmt.github.user.model.UserModel
import com.fmt.github.user.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.common_recyclerview.*
import androidx.core.app.ActivityOptionsCompat


class UserFragment : BaseVMFragment<UserViewModel>(), BaseQuickAdapter.OnItemClickListener,

    BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    private val mUserAdapter by lazy { UserAdapter() }

    override fun getLayoutRes(): Int = R.layout.common_recyclerview

    override fun initViewModel(): UserViewModel = get(UserViewModel::class.java)

    var mPage = 1
    var mSearchKey: String = ""

    override fun initView() {
        initSwipeRefreshLayout()
        initRecyclerView()
    }

    private fun initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(this)
    }

    private fun initRecyclerView() {
        with(mUserAdapter) {
            disableLoadMoreIfNotFullPage(mRecyclerView)
            onItemClickListener = this@UserFragment
            setOnLoadMoreListener(this@UserFragment, mRecyclerView)
            mRecyclerView
        }.apply {
            layoutManager = LinearLayoutManager(mActivity)
            adapter = mUserAdapter.apply { setEmptyView(R.layout.layout_empty) }
        }
    }

    override fun onRefresh() {
        mPage = 1
        searchUsers()
    }

    override fun onLoadMoreRequested() {
        mPage++
        searchUsers()
    }

    fun searchUsersByKey(searchKey: String = "") {
        if (!searchKey.isNullOrEmpty()) {
            mSearchKey = searchKey
            mPage = 1
        }
        searchUsers()
    }

    private fun searchUsers() {
        if (mPage == 1) mSwipeRefreshLayout.isRefreshing = true
        mViewModel.searchUsers(mSearchKey, mPage).observe(this, mSearchUserListObserver)

    }

    private val mSearchUserListObserver = Observer<UserListModel> {
        dealUserList(it.items)
    }

    private fun dealUserList(items: List<UserModel>) {
        if (mPage == 1) {
            mSwipeRefreshLayout.isRefreshing = false
            mUserAdapter.setNewData(items)
        } else {
            mUserAdapter.addData(items)
        }
        if (items == null || items.isEmpty()) {
            mUserAdapter.loadMoreEnd(true)
        } else {
            mUserAdapter.loadMoreComplete()
        }
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        with(Intent(mActivity, UserInfoActivity::class.java)) {
            putExtra(UserInfoActivity.USER_INFO, mUserAdapter.data[position])
        }.run {
            //共享元素共享动画
            val optionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity, view.findViewById(R.id.iv_head), "image")
            startActivity(this, optionsCompat.toBundle())
        }
    }

    override fun handleError() {
        mSwipeRefreshLayout.isRefreshing = false
    }
}