package com.fmt.github.user.fragment

import android.content.Intent
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.fmt.github.BR
import com.fmt.github.R
import com.fmt.github.base.fragment.BaseListMVFragment
import com.fmt.github.base.viewmodel.BaseViewModel
import com.fmt.github.databinding.LayoutUsersBinding
import com.fmt.github.user.activity.UserInfoActivity
import com.fmt.github.user.model.UserModel
import com.fmt.github.user.viewmodel.UserViewModel
import com.github.nitrico.lastadapter.LastAdapter
import com.github.nitrico.lastadapter.Type
import kotlinx.android.synthetic.main.common_refresh_recyclerview.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserFragment : BaseListMVFragment<UserModel>() {

    private val mViewModel: UserViewModel by viewModel()
    private var mSearchKey = ""
    private var mSort = ""//排序类型
    private var mOrder = ""//升序/降序

    override fun getViewModel(): BaseViewModel = mViewModel

    override fun initRecyclerView() {
        val type = Type<LayoutUsersBinding>(R.layout.layout_users)
            .onClick {
                go2UserInfoActivity(it.binding.ivHead, mListData[it.adapterPosition])
            }
        LastAdapter(mListData, BR.item)//基于DataBinding封装简化RecyclerView.Adapter
            .map<UserModel>(type)
            .into(mRecyclerView.apply {
                layoutManager = LinearLayoutManager(mActivity)
            })
    }

    override fun getListData() {
        mViewModel.searchUsers(mSearchKey, mSort, mOrder, mPage).observe(this, mListObserver)
    }

    fun searchUsersByKey(searchKey: String = "", sort: String, order: String) {
        mSearchKey = searchKey
        mSort = sort
        mOrder = order
        mRefreshLayout.autoRefresh()
    }

    private fun go2UserInfoActivity(view: View, userModel: UserModel) {
        with(Intent(mActivity, UserInfoActivity::class.java)) {
            putExtra(UserInfoActivity.USER_INFO, userModel)
        }.run {
            //共享元素共享动画
            ActivityOptionsCompat.makeSceneTransitionAnimation(
                mActivity,
                view.findViewById(R.id.iv_head),
                "image"
            )
                .toBundle()
                .also { bundle ->
                    startActivity(this, bundle)
                }
        }
    }
}