package com.lowe.wanandroid.ui.tools

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lowe.multitype.MultiTypeAdapter
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.ActivityToolsBinding
import com.lowe.wanandroid.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ToolListActivity :
    BaseActivity<ToolListViewModel, ActivityToolsBinding>(R.layout.activity_tools) {

    private val toolAdapter = MultiTypeAdapter().apply {
        register(ToolItemBinder())
    }

    override val viewModel: ToolListViewModel by viewModels()

    override fun init(savedInstanceState: Bundle?) {
        initView()
        initEvents()
    }

    private fun initView() {
        viewDataBinding.apply {
            with(toolList) {
                adapter = toolAdapter
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
            }
            with(toolbar) {
                setNavigationOnClickListener { finish() }
            }
        }
    }

    private fun initEvents() {
        viewModel.toolsLiveData.observe(this) {
            toolAdapter.items = it
            toolAdapter.notifyItemRangeInserted(0, toolAdapter.itemCount)
        }
    }
}