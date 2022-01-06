package com.fmt.github.base.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.fmt.github.base.viewmodel.BaseViewModel
import com.fmt.github.base.viewmodel.ErrorState
import com.fmt.github.base.viewmodel.LoadState
import com.fmt.github.base.viewmodel.SuccessState
import com.fmt.github.ext.errorToast

/**
 * Fragment懒加载
 * 采用ViewPager2 + FragmentStateAdapter + onResume方法实现
 */
abstract class BaseVMFragment : Fragment() {

    protected var mRootView: View? = null

    private var mIsHasData = false//是否加载过数据

    lateinit var mActivity: AppCompatActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as AppCompatActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModelAction()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mRootView == null) {
            mRootView = View.inflate(mActivity, getLayoutRes(), null)
        }
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    //onResume方法判断是否已经加载过数据
    override fun onResume() {
        super.onResume()
        lazyLoadData()
    }

    private fun lazyLoadData() {
        if (!mIsHasData) {
            mIsHasData = true
            initData()
        }
    }
    //监听页面的三种状态：加载中、加载成功、加载失败
    private fun initViewModelAction() {
        this.getViewModel().let { baseViewModel ->
            baseViewModel.mStateLiveData.observe(this, { stateActionState ->
                when (stateActionState) {
                    LoadState -> showLoading()
                    SuccessState -> dismissLoading()
                    else -> {
                        dismissLoading()
                        (stateActionState as ErrorState).message?.apply {
                            errorToast(this)
                            handleError()
                        }
                    }
                }
            })
        }
    }

    abstract fun getLayoutRes(): Int

    abstract fun getViewModel(): BaseViewModel

    abstract fun initView()

    open fun initData() {

    }

    open fun showLoading() {

    }

    open fun dismissLoading() {

    }

    open fun handleError() {

    }
}