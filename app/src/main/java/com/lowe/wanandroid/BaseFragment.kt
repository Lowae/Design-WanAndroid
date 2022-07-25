package com.lowe.wanandroid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.lowe.common.base.BaseViewModel

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
        onViewCreated(savedInstanceState)
    }

    protected abstract fun onViewCreated(savedInstanceState: Bundle?)

}