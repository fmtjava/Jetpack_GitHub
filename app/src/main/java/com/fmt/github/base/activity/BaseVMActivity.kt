package com.fmt.github.base.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fmt.github.App
import com.fmt.github.base.viewmodel.BaseViewModel
import com.fmt.github.ext.errorToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

/**
 * 封装带有协程基类
 */
abstract class BaseVMActivity<VM : BaseViewModel> : AppCompatActivity(), CoroutineScope by MainScope() {

    lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        mViewModel = initViewModel()
        initViewModelAction()
        initView()
        initData()
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

    abstract fun getLayoutId(): Int

    abstract fun initView()

    abstract fun initViewModel(): VM

    open fun initData() {

    }

    open fun handleError() {

    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()//取消任务
    }

}