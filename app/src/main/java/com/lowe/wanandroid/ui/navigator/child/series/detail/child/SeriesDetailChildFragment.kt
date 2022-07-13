package com.lowe.wanandroid.ui.navigator.child.series.detail.child

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.lowe.multitype.PagingLoadStateAdapter
import com.lowe.multitype.PagingMultiTypeAdapter
import com.lowe.wanandroid.R
import com.lowe.wanandroid.base.app.AppViewModel
import com.lowe.wanandroid.compat.BundleCompat
import com.lowe.wanandroid.databinding.FragmentSeriesDetailChildBinding
import com.lowe.wanandroid.services.model.Article
import com.lowe.wanandroid.services.model.Classify
import com.lowe.wanandroid.services.model.CollectEvent
import com.lowe.wanandroid.ui.ArticleDiffCalculator
import com.lowe.wanandroid.ui.BaseFragment
import com.lowe.wanandroid.ui.SimpleFooterItemBinder
import com.lowe.wanandroid.ui.home.item.ArticleAction
import com.lowe.wanandroid.ui.home.item.HomeArticleItemBinderV2
import com.lowe.wanandroid.ui.navigator.child.series.detail.SeriesDetailListViewModel
import com.lowe.wanandroid.ui.web.WebActivity
import com.lowe.wanandroid.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 体系tag下的子Fragment
 */
@AndroidEntryPoint
class SeriesDetailChildFragment :
    BaseFragment<SeriesDetailChildViewModel, FragmentSeriesDetailChildBinding>(R.layout.fragment_series_detail_child) {

    companion object {

        const val KEY_SERIES_DETAIL_CHILD_TAB = "key_series_detail_child_tab"

        fun newInstance(classify: Classify) =
            with(SeriesDetailChildFragment()) {
                arguments = bundleOf(KEY_SERIES_DETAIL_CHILD_TAB to classify)
                this
            }
    }

    @Inject
    lateinit var appViewModel: AppViewModel

    private val classify by lazy(LazyThreadSafetyMode.NONE) {
        BundleCompat.getParcelable(arguments, KEY_SERIES_DETAIL_CHILD_TAB) ?: Classify()
    }
    private val detailsAdapter =
        PagingMultiTypeAdapter(ArticleDiffCalculator.getCommonDiffItemCallback()).apply {
            register(HomeArticleItemBinderV2(this@SeriesDetailChildFragment::onItemClick))
        }
    private val seriesDetailViewModel by activityViewModels<SeriesDetailListViewModel>()

    override val viewModel: SeriesDetailChildViewModel by viewModels()

    override fun onViewCreated(savedInstanceState: Bundle?) {
        initView()
        initEvents()
    }

    private fun initView() {
        viewDataBinding.apply {
            with(seriesDetailList) {
                setHasFixedSize(true)
                adapter = detailsAdapter.withLoadStateFooter(
                    PagingLoadStateAdapter(
                        SimpleFooterItemBinder(), detailsAdapter.types
                    )
                )
                layoutManager = LinearLayoutManager(context)
            }
        }
    }

    private fun initEvents() {
        repeatOnStarted {
            launch {
                viewModel.getSeriesDetailListFlow(classify.id).collectLatest(detailsAdapter::submitData)
            }

            launch {
                detailsAdapter.loadStateFlow.collect(this@SeriesDetailChildFragment::updateLoadStates)
            }
        }
        seriesDetailViewModel.onRefreshLiveData.observe(viewLifecycleOwner) {
            if (it.id == classify.id) detailsAdapter.refresh()
        }
        appViewModel.collectArticleEvent.observe(viewLifecycleOwner) { event ->
            detailsAdapter.snapshot().run {
                val index = indexOfFirst { it is Article && it.id == event.id }
                if (index >= 0) {
                    (this[index] as? Article)?.collect = event.isCollected
                    index
                } else null
            }?.apply(detailsAdapter::notifyItemChanged)
        }
    }

    private fun onItemClick(articleAction: ArticleAction) {
        when (articleAction) {
            is ArticleAction.ItemClick -> WebActivity.loadUrl(
                requireContext(),
                Activities.Web.WebIntent(
                    articleAction.article.link,
                    articleAction.article.id,
                    articleAction.article.collect,
                )
            )
            is ArticleAction.CollectClick -> {
                appViewModel.articleCollectAction(
                    CollectEvent(
                        articleAction.article.id,
                        articleAction.article.link,
                        articleAction.article.collect.not()
                    )
                )
            }
            is ArticleAction.AuthorClick -> {
                startActivity(
                    intentTo(
                        Activities.ShareList(bundle = bundleOf(Activities.ShareList.KEY_SHARE_LIST_USER_ID to articleAction.article.userId.toString()))
                    )
                )
            }
        }
    }

    private fun updateLoadStates(loadStates: CombinedLoadStates) {
        viewDataBinding.loadingContainer.apply {
            emptyLayout.isVisible =
                loadStates.refresh is LoadState.NotLoading && detailsAdapter.isEmpty()
            loadingProgress.isVisible = detailsAdapter.isEmpty() && loadStates.isRefreshing
        }
    }
}