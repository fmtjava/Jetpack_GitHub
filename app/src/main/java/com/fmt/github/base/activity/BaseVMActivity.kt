package com.fmt.github.base.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fmt.github.App
import com.fmt.github.base.viewmodel.*
import com.fmt.github.ext.errorToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

/**
 * 封装带有协程基类,使用代理类完成
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

    abstract fun getLayoutId(): Int

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
        cancel()//取消协程任务
    }

}