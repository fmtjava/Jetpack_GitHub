package com.fmt.github.base.activity

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * 封装带有协程基类(DataBinding + ViewModel),使用代理类完成
 *
 */
abstract class BaseDataBindVMActivity<DB : ViewDataBinding> : BaseVMActivity() {

    lateinit var mDataBind: DB

    override fun setContentLayout() {
        mDataBind = DataBindingUtil.setContentView(this, getLayoutId())
        mDataBind.lifecycleOwner = this
        initViewModelAction()
        initView()
        initData()
    }

    override fun onDestroy() {
        super.onDestroy()
        mDataBind.unbind()
    }

}