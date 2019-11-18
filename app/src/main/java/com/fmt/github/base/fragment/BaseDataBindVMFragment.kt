package com.fmt.github.base.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.fmt.github.base.viewmodel.*
import com.fmt.github.ext.errorToast

/**
 * Fragment懒加载(DataBinding + ViewModel)
 */
abstract class BaseDataBindVMFragment<DB : ViewDataBinding, VM : BaseViewModel> : Fragment() {

    private var mRootView: View? = null

    lateinit var mDataBind: DB

    lateinit var mViewModel: VM

    private var mIsCreateView: Boolean = false

    private var mIsHasData: Boolean = false

    lateinit var mActivity: AppCompatActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as AppCompatActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = initViewModel()
        initViewModelAction()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mRootView == null) {
            mDataBind = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
            // 让xml内绑定的LiveData和Observer建立连接，让LiveData能感知Activity的生命周期
            mDataBind.lifecycleOwner = this
            mRootView = mDataBind.root
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

    //初始化通用事件驱动(如：显示对话框、关闭对话框、统一错误提示)
    private fun initViewModelAction() {
        if (mViewModel is BaseViewModel) {
            mViewModel.mStateLiveData.observe(this, Observer {
                when (it) {
                    LoadState -> showLoading()
                    SuccessState -> dismissLoading()
                    is ErrorState -> {
                        dismissLoading()
                        it.message?.apply {
                            errorToast(this)
                            handleError()
                        }
                    }
                }
            })
        }
    }

    abstract fun getLayoutRes(): Int

    abstract fun initView()

    abstract fun initViewModel(): VM

    open fun initData() {

    }

    open fun showLoading() {

    }

    open fun dismissLoading() {

    }

    open fun handleError(){

    }

    override fun onDestroy() {
        super.onDestroy()
        mDataBind.unbind()
    }

}