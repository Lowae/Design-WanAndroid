package com.lowe.wanandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.lowe.wanandroid.base.AppLog
import com.lowe.wanandroid.utils.showShortToast
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseFragment<VM : BaseViewModel, VD : ViewDataBinding>(@LayoutRes private val layoutResId: Int) :
    Fragment() {

    protected lateinit var viewDataBinding: VD
    protected abstract val viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = DataBindingUtil.inflate<VD>(inflater, layoutResId, container, false)
        .also {
            it.lifecycleOwner = viewLifecycleOwner
            viewDataBinding = it
        }
        .root

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init()
        viewModel.requestException.observe(viewLifecycleOwner) {
            AppLog.e(msg = "错误：${it.message}")
            when (it) {
                is SocketTimeoutException -> "网络请求超时".showShortToast()
                is ConnectException, is UnknownHostException -> "网络连接失败".showShortToast()
                else -> "网络请求失败".showShortToast()
            }
        }
        onViewCreated(savedInstanceState)
    }

    protected abstract fun onViewCreated(savedInstanceState: Bundle?)

}