package com.lowe.wanandroid.ui.search.result

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.lowe.wanandroid.BaseFragment
import com.lowe.common.base.app.AppViewModel
import com.lowe.common.services.model.Article
import com.lowe.common.services.model.CollectEvent
import com.lowe.common.utils.Activities
import com.lowe.common.utils.isEmpty
import com.lowe.common.utils.isRefreshing
import com.lowe.common.utils.launchRepeatOnStarted
import com.lowe.multitype.PagingLoadStateAdapter
import com.lowe.multitype.PagingMultiTypeAdapter
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.FragmentSearchResultBinding
import com.lowe.wanandroid.ui.ArticleDiffCalculator
import com.lowe.wanandroid.ui.SimpleFooterItemBinder
import com.lowe.wanandroid.ui.home.item.ArticleAction
import com.lowe.wanandroid.ui.home.item.HomeArticleItemBinderV2
import com.lowe.wanandroid.ui.search.SearchViewModel
import com.lowe.wanandroid.ui.web.WebActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 搜索结果列表Fragment
 */
@AndroidEntryPoint
class SearchListFragment :
    BaseFragment<SearchListViewModel, FragmentSearchResultBinding>(R.layout.fragment_search_result) {

    private val searchListAdapter =
        PagingMultiTypeAdapter(ArticleDiffCalculator.getCommonDiffItemCallback()).apply {
            register(HomeArticleItemBinderV2(this@SearchListFragment::onArticleClick))
        }

    @Inject
    lateinit var appViewModel: AppViewModel

    private val searchActivityViewModel by activityViewModels<SearchViewModel>()

    override val viewModel: SearchListViewModel by viewModels()

    override fun onViewCreated(savedInstanceState: Bundle?) {
        initView()
        initEvent()
    }

    private fun initView() {
        viewDataBinding.searchResultList.apply {
            adapter = searchListAdapter.withLoadStateFooter(PagingLoadStateAdapter(SimpleFooterItemBinder(), searchListAdapter.types))
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }

    private fun initEvent() {
        launchRepeatOnStarted {
            launch {
                searchActivityViewModel.pagingDataFlow.collectLatest(searchListAdapter::submitData)
            }

            launch {
                searchListAdapter.loadStateFlow.collectLatest { loadState ->
                    updateLoadStates(loadState)
                    if (loadState.refresh == LoadState.Loading) {
                        viewDataBinding.searchResultList.scrollToPosition(0)
                    }
                }
            }
        }
        appViewModel.collectArticleEvent.observe(viewLifecycleOwner) { event ->
            searchListAdapter.snapshot().run {
                val index = indexOfFirst { it is Article && it.id == event.id }
                if (index >= 0) {
                    (this[index] as? Article)?.collect = event.isCollected
                    index
                } else null
            }?.apply(searchListAdapter::notifyItemChanged)
        }
    }

    private fun onArticleClick(articleAction: ArticleAction) {
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
            else -> {}
        }
    }

    private fun updateLoadStates(loadStates: CombinedLoadStates) {
        viewDataBinding.loadingContainer.apply {
            emptyLayout.isVisible =
                loadStates.refresh is LoadState.NotLoading && searchListAdapter.isEmpty()
            loadingProgress.isVisible = loadStates.isRefreshing
        }
    }
}