package com.lowe.wanandroid.ui.collect

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.lowe.multitype.paging.MultiTypePagingAdapter
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.ActivityCollectBinding
import com.lowe.wanandroid.services.model.CollectBean
import com.lowe.wanandroid.ui.ActivityDataBindingDelegate
import com.lowe.wanandroid.ui.ArticleDiffCalculator
import com.lowe.wanandroid.ui.BaseActivity
import com.lowe.wanandroid.ui.collect.item.CollectItemBinder
import com.lowe.wanandroid.ui.web.WebActivity
import com.lowe.wanandroid.utils.ToastEx.showShortToast
import com.lowe.wanandroid.utils.isEmpty
import com.lowe.wanandroid.utils.isRefreshing
import com.lowe.wanandroid.utils.whenError
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class CollectActivity : BaseActivity<CollectViewModel, ActivityCollectBinding>() {

    private val collectPagingAdapter =
        MultiTypePagingAdapter(ArticleDiffCalculator.getCommonArticleDiffItemCallback()).apply {
            register(CollectItemBinder(this@CollectActivity::onCollectClick))
        }

    override val viewDataBinding: ActivityCollectBinding by ActivityDataBindingDelegate(R.layout.activity_collect)

    override val viewModel: CollectViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initEvents()
    }

    private fun initView() {
        viewDataBinding.apply {
            with(collectList) {
                adapter = collectPagingAdapter
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
            }
            with(toolbar) {
                setNavigationOnClickListener {
                    finish()
                }
            }
        }
    }

    private fun initEvents() {
        lifecycleScope.launchWhenCreated {
            collectPagingAdapter.loadStateFlow.collectLatest { loadState ->
                loadState.whenError { it.error.message?.showShortToast() }
            }
        }
        lifecycleScope.launchWhenCreated {
            collectPagingAdapter.loadStateFlow.collect(this@CollectActivity::updateLoadStates)
        }
        lifecycleScope.launchWhenCreated {
            viewModel.collectFlow().collectLatest(collectPagingAdapter::submitData)
        }
    }

    private fun onCollectClick(position: Int, collectBean: CollectBean) {
        WebActivity.loadUrl(this, collectBean.link)
    }

    private fun updateLoadStates(loadStates: CombinedLoadStates) {
        viewDataBinding.loadingContainer.apply {
            emptyLayout.isVisible =
                loadStates.refresh is LoadState.NotLoading && collectPagingAdapter.isEmpty()
            loadingProgress.isVisible = collectPagingAdapter.isEmpty() && loadStates.isRefreshing
        }
    }
}