package com.lowe.wanandroid

import androidx.databinding.ViewDataBinding
import com.lowe.common.base.BaseThemeActivity
import com.lowe.common.base.BaseViewModel

abstract class BaseActivity<VM : BaseViewModel, VD : ViewDataBinding> : BaseThemeActivity() {

    protected abstract val viewDataBinding: VD
    protected abstract val viewModel: VM

}