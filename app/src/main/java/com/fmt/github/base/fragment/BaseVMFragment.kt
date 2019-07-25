package com.fmt.github.base.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fmt.github.App
import com.fmt.github.base.viewmodel.BaseViewModel
import com.fmt.github.ext.errorToast

/**
 * Fragment懒加载
 */
abstract class BaseVMFragment<VM : BaseViewModel> : Fragment() {

    private var mRootView: View? = null

    lateinit var mViewModel: VM

    private var mIsCreateView: Boolean = false

    private var mIsHasData: Boolean = false

    lateinit var mActivity: Activity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as Activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = initViewModel()
        initViewModelAction()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mRootView == null) {
            mRootView = View.inflate(container?.context, getLayoutRes(), null)
        }
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mIsCreateView = true
        initView()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && mIsCreateView) {//可见并且view被创建后后才加载数据
            lazyLoadData()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {//防止和ViewPager结合使用时，第一个Fragment空白问题
        super.onActivityCreated(savedInstanceState)
        if (userVisibleHint) {//可见时才加载数据
            lazyLoadData()
        }
    }

    private fun lazyLoadData() {
        if (!mIsHasData) {
            mIsHasData = true
            initData()
        }
    }

    fun <VM : ViewModel> get(modelClass: Class<VM>): VM {//获取ViewModel
        val viewModelFactory = ViewModelProvider.AndroidViewModelFactory(App.mApplication)
        val viewModelProvider = ViewModelProvider(this, viewModelFactory)
        return viewModelProvider.get(modelClass)
    }

    private fun initViewModelAction() {
        if (mViewModel is BaseViewModel) {
            mViewModel.mErrorLiveData.observe(this, Observer { error ->
                error?.let {
                    errorToast(it)
                }
                handleError()
            })
        }
    }

    abstract fun getLayoutRes(): Int

    abstract fun initView()

    abstract fun initViewModel(): VM

    open fun initData() {

    }

    open fun handleError() {

    }

}