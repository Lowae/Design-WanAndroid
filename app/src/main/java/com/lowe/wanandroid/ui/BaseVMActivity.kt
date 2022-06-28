package com.lowe.wanandroid.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding

abstract class BaseVMActivity<VM : BaseViewModel, VD : ViewDataBinding> : AppCompatActivity() {

    protected abstract val viewDataBinding: VD
    protected abstract val viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.init()
    }
}