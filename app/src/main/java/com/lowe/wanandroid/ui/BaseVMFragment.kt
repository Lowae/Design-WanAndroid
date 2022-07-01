package com.lowe.wanandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.lowe.wanandroid.base.AppLog
import com.lowe.wanandroid.utils.showLongToast
import com.lowe.wanandroid.utils.showShortToast
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


abstract class BaseVMFragment<VM : BaseViewModel, VB : ViewDataBinding>(private val layoutResId: Int) :
    Fragment() {

    protected lateinit var viewBinding: VB
    protected abstract val viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = DataBindingUtil.inflate<VB>(inflater, layoutResId, container, false)
        .also {
            it.lifecycleOwner = viewLifecycleOwner
            viewBinding = it
        }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init()
        init(savedInstanceState)
        viewModel.apply {
            requestException.observe(viewLifecycleOwner) {
                AppLog.e(msg = "错误：${it.message}")
                when (it) {
                    is SocketTimeoutException -> "网络请求超时".showShortToast()
                    is ConnectException, is UnknownHostException -> "网络连接失败".showShortToast()
                    else -> "网络请求失败".showShortToast()
                }
            }

            responseException.observe(viewLifecycleOwner) {
                it.errorMsg.showLongToast()
            }
        }
    }

    protected abstract fun init(savedInstanceState: Bundle?)
}