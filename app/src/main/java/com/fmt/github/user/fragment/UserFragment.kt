package com.fmt.github.user.fragment

import android.content.Intent
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.fmt.github.BR
import com.fmt.github.R
import com.fmt.github.base.fragment.BaseVMFragment
import com.fmt.github.databinding.LayoutUsersBinding
import com.fmt.github.ext.otherwise
import com.fmt.github.ext.yes
import com.fmt.github.user.activity.UserInfoActivity
import com.fmt.github.user.model.UserListModel
import com.fmt.github.user.model.UserModel
import com.fmt.github.user.viewmodel.UserViewModel
import com.fmt.github.ext.of
import com.github.nitrico.lastadapter.LastAdapter
import com.github.nitrico.lastadapter.Type
import com.kennyc.view.MultiStateView
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.common_refresh_recyclerview.*

class UserFragment : BaseVMFragment<UserViewModel>(), OnRefreshListener, OnLoadMoreListener {

    override fun getLayoutRes(): Int = R.layout.common_refresh_recyclerview

    override fun initViewModel(): UserViewModel = of(mActivity, UserViewModel::class.java)

    private var mPage = 1
    var mSearchKey: String = ""

    private val mUserList = ObservableArrayList<UserModel>()

    override fun initView() {
        initRefreshLayout()
        initRecyclerView()
    }

    private fun initRefreshLayout() {
        mRefreshLayout.setOnRefreshListener(this)
        mRefreshLayout.setOnLoadMoreListener(this)
    }

    private fun initRecyclerView() {
        val type = Type<LayoutUsersBinding>(R.layout.layout_users)
            .onClick {
                go2UserInfoActivity(it.binding.ivHead, mUserList[it.adapterPosition])
            }
        LastAdapter(mUserList, BR.item)//基于DataBinding封装简化RecyclerView.Adapter
            .map<UserModel>(type)
            .into(mRecyclerView.apply {
                layoutManager = LinearLayoutManager(mActivity)
            })
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mPage = 1
        searchUsers()
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        mPage++
        searchUsers()
    }

    fun searchUsersByKey(searchKey: String = "") {
        mSearchKey = searchKey
        mRefreshLayout.autoRefresh()
    }

    private fun searchUsers() {
        mViewModel.searchUsers(mSearchKey, mPage).observe(this, mSearchUserListObserver)
    }

    private val mSearchUserListObserver = Observer<UserListModel> {
        dealUserList(it.items)
    }

    private fun dealUserList(items: List<UserModel>) {
        (items != null && items.isNotEmpty()).yes {
            mMultipleStatusView.viewState = MultiStateView.ViewState.CONTENT
        }
        (mPage == 1).yes {
            mUserList.clear()
            mUserList.addAll(items)
            mRefreshLayout.finishRefresh()
        }.otherwise {
            mUserList.addAll(items)
            mRefreshLayout.finishLoadMore()
        }
    }

    private fun go2UserInfoActivity(view: View, userModel: UserModel) {
        with(Intent(mActivity, UserInfoActivity::class.java)) {
            putExtra(UserInfoActivity.USER_INFO, userModel)
        }.run {
            //共享元素共享动画
            val optionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity, view.findViewById(R.id.iv_head), "image")
            startActivity(this, optionsCompat.toBundle())
        }
    }

    override fun dismissLoading() {
        mRefreshLayout.finishRefresh()
        mRefreshLayout.finishLoadMore()
    }

}