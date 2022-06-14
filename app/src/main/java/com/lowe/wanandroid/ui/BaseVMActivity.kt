package com.lowe.wanandroid.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseVMActivity<VM : BaseViewModel, VD : ViewDataBinding>(private val layoutResId: Int) :
    AppCompatActivity() {

    lateinit var viewDataBinding: VD
    protected abstract val viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        initDataBinding()
        init(savedInstanceState)
    }

    private fun initViewModel() {
        viewModel.start()
    }


    private fun initDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView<VD>(this, layoutResId).apply {
            lifecycleOwner = this@BaseVMActivity
        }
    }

    abstract fun init(savedInstanceState: Bundle?)
}