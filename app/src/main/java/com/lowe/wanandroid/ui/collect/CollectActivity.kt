package com.lowe.wanandroid.ui.collect

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.lowe.multitype.paging.MultiTypeLoadStateAdapter
import com.lowe.multitype.paging.MultiTypePagingAdapter
import com.lowe.wanandroid.R
import com.lowe.wanandroid.base.LoadingItemDelegate
import com.lowe.wanandroid.databinding.ActivityCollectBinding
import com.lowe.wanandroid.services.model.CollectBean
import com.lowe.wanandroid.ui.ArticleDiffCalculator
import com.lowe.wanandroid.ui.BaseActivity
import com.lowe.wanandroid.ui.collect.item.CollectItemBinder
import com.lowe.wanandroid.ui.web.WebActivity
import com.lowe.wanandroid.utils.ToastEx.showShortToast
import com.lowe.wanandroid.utils.whenError
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class CollectActivity :
    BaseActivity<CollectViewModel, ActivityCollectBinding>(R.layout.activity_collect) {

    private val collectPagingAdapter =
        MultiTypePagingAdapter(ArticleDiffCalculator.getCommonArticleDiffItemCallback()).apply {
            register(CollectItemBinder(this@CollectActivity::onCollectClick))
        }

    private val loadingAdapter = MultiTypeLoadStateAdapter().apply {
        register(LoadingItemDelegate())
    }

    override val viewModel: CollectViewModel by viewModels()

    override fun init(savedInstanceState: Bundle?) {
        initView()
        initEvents()
    }

    private fun initView() {
        viewDataBinding.apply {
            with(collectList) {
                adapter = collectPagingAdapter.withLoadStateFooter(loadingAdapter)
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
            viewModel.collectFlow().collectLatest(collectPagingAdapter::submitData)
        }
    }

    private fun onCollectClick(position: Int, collectBean: CollectBean) {
        WebActivity.loadUrl(this, collectBean.link)
    }

}