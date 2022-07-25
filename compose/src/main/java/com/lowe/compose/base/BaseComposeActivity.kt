package com.lowe.compose.base

import androidx.activity.ComponentActivity
import com.lowe.common.base.BaseViewModel

abstract class BaseComposeActivity<VM : BaseViewModel>: ComponentActivity() {

    protected abstract val viewModel: VM

}