package com.fmt.github.base.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import com.fmt.github.base.viewmodel.BaseViewModel
import com.fmt.github.base.viewmodel.ErrorState
import com.fmt.github.base.viewmodel.LoadState
import com.fmt.github.base.viewmodel.SuccessState
import com.fmt.github.ext.errorToast

abstract class BaseVMActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentLayout()
    }

    protected fun initViewModelAction() {
        getViewModel().let { baseViewModel ->
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

    abstract fun getLayoutId(): Int

    abstract fun initView()

    abstract fun getViewModel(): BaseViewModel

    open fun setContentLayout() {
        setContentView(getLayoutId())
        initViewModelAction()
        initView()
        initData()
    }

    open fun initData() {

    }

    open fun showLoading() {

    }

    open fun dismissLoading() {

    }

    open fun handleError() {

    }

    protected fun <T : Any> LiveData<T>.observeKt(block: (T) -> Unit) {
        this.observe(this@BaseVMActivity) {
            block(it)
        }
    }
}