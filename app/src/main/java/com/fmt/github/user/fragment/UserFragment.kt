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
import com.fmt.github.base.viewmodel.BaseViewModel
import com.fmt.github.databinding.LayoutUsersBinding
import com.fmt.github.ext.otherwise
import com.fmt.github.ext.yes
import com.fmt.github.user.activity.UserInfoActivity
import com.fmt.github.user.model.UserListModel
import com.fmt.github.user.model.UserModel
import com.fmt.github.user.viewmodel.UserViewModel
import com.github.nitrico.lastadapter.LastAdapter
import com.github.nitrico.lastadapter.Type
import com.kennyc.view.MultiStateView
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.common_refresh_recyclerview.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserFragment : BaseVMFragment(), OnRefreshListener, OnLoadMoreListener {

    private val mViewModel: UserViewModel by viewModel()

    override fun getLayoutRes(): Int = R.layout.common_refresh_recyclerview

    private var mPage = 1
    var mSearchKey: String = ""
    private var mSort: String = ""//排序类型
    private var mOrder: String = ""//升序/降序

    private val mUserList = ObservableArrayList<UserModel>()

    override fun initView() {
        initRefreshLayout()
        initRecyclerView()
    }

    override fun getViewModel(): BaseViewModel = mViewModel

    private fun initRefreshLayout() {
        mRefreshLayout.run {
            setOnRefreshListener(this@UserFragment)
            setOnLoadMoreListener(this@UserFragment)
        }
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

    fun searchUsersByKey(searchKey: String = "", sort: String, order: String) {
        mSearchKey = searchKey
        mSort = sort
        mOrder = order
        mRefreshLayout.autoRefresh()
    }

    private fun searchUsers() {
        mViewModel.searchUsers(mSearchKey, mSort, mOrder, mPage).observe(this, mSearchUserListObserver)
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
            ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity, view.findViewById(R.id.iv_head), "image")
                .toBundle()
                .also { bundle ->
                    startActivity(this, bundle)
                }
        }
    }

    override fun dismissLoading() {
        mRefreshLayout.run {
            finishRefresh()
            finishLoadMore()
        }
    }

}