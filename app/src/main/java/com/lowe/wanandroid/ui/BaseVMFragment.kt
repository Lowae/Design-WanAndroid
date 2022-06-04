package com.lowe.wanandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.lowe.wanandroid.BR
import com.lowe.wanandroid.base.AppLog
import com.lowe.wanandroid.utils.ToastUtil
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


abstract class BaseVMFragment<VM : BaseViewModel, VB : ViewDataBinding>(private val layoutResId: Int) :
    Fragment() {

    protected lateinit var viewModel: VM
    protected lateinit var viewBinding: VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        init(savedInstanceState)
        setupDataBinding()
        viewModel.apply {
            requestException.observe(viewLifecycleOwner) {
                AppLog.e(msg = "网络请求错误：${it.message}")
                when (it) {
                    is SocketTimeoutException -> ToastUtil.showShort(
                        requireContext(),
                        "网络请求超时"
                    )
                    is ConnectException, is UnknownHostException -> ToastUtil.showShort(
                        requireContext(),
                        "网络连接失败"
                    )
                    else -> ToastUtil.showShort(
                        requireContext(), it.message ?: "网络请求失败"
                    )
                }
            }

            responseException.observe(viewLifecycleOwner) {
                ToastUtil.showLong(requireContext(), it.errorMsg)
            }
        }
    }

    protected abstract fun createViewModel(): VM

    /**
     * View初始化
     */
    protected abstract fun init(savedInstanceState: Bundle?)

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[createViewModel()::class.java]
        viewModel.start()
    }


    /**
     * DataBinding相关设置
     */
    private fun setupDataBinding() {
        viewBinding.apply {
            // 需绑定lifecycleOwner到Fragment,xml绑定的数据才会随着liveData数据源的改变而改变
            lifecycleOwner = viewLifecycleOwner
            setVariable(BR.viewModel, viewModel)
        }
    }


}