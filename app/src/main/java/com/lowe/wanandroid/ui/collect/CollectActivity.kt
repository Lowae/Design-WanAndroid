package com.lowe.wanandroid.ui.collect

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.lowe.multitype.PagingLoadStateAdapter
import com.lowe.multitype.PagingMultiTypeAdapter
import com.lowe.wanandroid.R
import com.lowe.wanandroid.base.app.AppViewModel
import com.lowe.wanandroid.databinding.ActivityCollectBinding
import com.lowe.wanandroid.services.model.CollectBean
import com.lowe.wanandroid.services.model.CollectEvent
import com.lowe.wanandroid.ui.*
import com.lowe.wanandroid.ui.collect.item.CollectItemBinder
import com.lowe.wanandroid.ui.web.WebActivity
import com.lowe.wanandroid.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

/**
 * 收藏页面
 */
@AndroidEntryPoint
class CollectActivity : BaseActivity<CollectViewModel, ActivityCollectBinding>() {

    @Inject
    lateinit var appViewModel: AppViewModel

    private val collectPagingAdapter =
        PagingMultiTypeAdapter(ArticleDiffCalculator.getCommonDiffItemCallback()).apply {
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
                adapter = collectPagingAdapter.withLoadStateFooter(
                    PagingLoadStateAdapter(
                        SimpleFooterItemBinder(),
                        collectPagingAdapter.types
                    )
                )
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
            }
            with(toolbar) {
                setNavigationOnClickListener {
                    finish()
                }
            }
            with(collectSwipeRefresh) {
                setColorSchemeColors(context.getPrimaryColor())
                setProgressBackgroundColorSchemeResource(R.color.md_theme_background)
                setOnRefreshListener {
                    collectPagingAdapter.refresh()
                    isRefreshing = false
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
            viewModel.collectFlow.collectLatest(collectPagingAdapter::submitData)
        }
        appViewModel.collectArticleEvent.observe(this) { event ->
            collectPagingAdapter.snapshot().run {
                val index = indexOfFirst { it is CollectBean && it.originId == event.id }
                if (index >= 0) {
                    (this[index] as? CollectBean)?.collect = event.isCollected
                    index
                } else null
            }?.apply(collectPagingAdapter::notifyItemChanged)
        }
    }

    private fun onCollectClick(position: Int, collectBean: CollectBean, type: ItemClickType) {
        when (type) {
            ItemClickType.CONTENT -> WebActivity.loadUrl(
                this,
                Activities.Web.WebIntent(collectBean.link, collectBean.originId, collectBean.collect)
            )
            ItemClickType.COLLECT -> {
                /**
                 * 这里需要使用的是[CollectBean.originId]，而不是id
                 */
                appViewModel.articleCollectAction(
                    CollectEvent(
                        collectBean.originId,
                        collectBean.link,
                        collectBean.collect.not()
                    )
                )
            }
        }
    }

    private fun updateLoadStates(loadStates: CombinedLoadStates) {
        viewDataBinding.loadingContainer.apply {
            emptyLayout.isVisible =
                loadStates.refresh is LoadState.NotLoading && collectPagingAdapter.isEmpty()
            loadingProgress.isVisible = collectPagingAdapter.isEmpty() && loadStates.isRefreshing
        }
    }
}