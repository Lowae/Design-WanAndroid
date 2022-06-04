package com.lowe.wanandroid.ui

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle

abstract class BaseFragment<VM : BaseViewModel, VB : ViewDataBinding>(layoutResId: Int) :
    BaseVMFragment<VM, VB>(layoutResId) {

    private var isFirstLoading = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isFirstLoading = true
    }

    override fun onResume() {
        super.onResume()
        if (lifecycle.currentState == Lifecycle.State.STARTED && isFirstLoading) {
            isFirstLoading = false
            lazyLoad()
        }
    }

    open fun lazyLoad() = Unit
}