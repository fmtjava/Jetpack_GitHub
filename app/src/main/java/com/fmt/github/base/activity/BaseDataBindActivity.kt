package com.fmt.github.base.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

abstract class BaseDataBindActivity<DB : ViewDataBinding> : AppCompatActivity(), CoroutineScope by MainScope() {

    lateinit var mDataBind: DB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDataBind = DataBindingUtil.setContentView(this,getLayoutId())
        initView()
        initData()
    }

    abstract fun getLayoutId(): Int

    abstract fun initView()

    open fun initData() {

    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
        mDataBind.unbind()
    }

}