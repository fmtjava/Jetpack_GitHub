package com.fmt.github.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * Fragment懒加载(DataBinding + ViewModel)
 */
abstract class BaseDataBindVMFragment<DB : ViewDataBinding> : BaseVMFragment() {

    lateinit var mDataBind: DB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mRootView == null) {
            mDataBind = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
            // 让xml内绑定的LiveData和Observer建立连接，让LiveData能感知Activity的生命周期
            mDataBind.lifecycleOwner = this
            mRootView = mDataBind.root
        }
        return mRootView
    }

    override fun onDestroy() {
        super.onDestroy()
        mDataBind.unbind()
    }
}