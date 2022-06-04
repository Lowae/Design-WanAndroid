package com.lowe.wanandroid.ui

import android.os.Bundle
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<VM : BaseViewModel, VD : ViewDataBinding>(layoutResId: Int) :
    BaseVMActivity<VM, VD>(layoutResId) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
    }
}