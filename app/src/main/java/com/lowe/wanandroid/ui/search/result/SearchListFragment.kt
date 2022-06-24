package com.lowe.wanandroid.ui.search.result

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.lowe.multitype.paging.MultiTypePagingAdapter
import com.lowe.wanandroid.R
import com.lowe.wanandroid.base.app.AppViewModel
import com.lowe.wanandroid.databinding.FragmentSearchResultBinding
import com.lowe.wanandroid.services.model.Article
import com.lowe.wanandroid.services.model.CollectEvent
import com.lowe.wanandroid.ui.ArticleDiffCalculator
import com.lowe.wanandroid.ui.BaseFragment
import com.lowe.wanandroid.ui.home.item.ArticleAction
import com.lowe.wanandroid.ui.home.item.HomeArticleItemBinderV2
import com.lowe.wanandroid.ui.search.SearchViewModel
import com.lowe.wanandroid.ui.web.WebActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class SearchListFragment :
    BaseFragment<SearchListViewModel, FragmentSearchResultBinding>(R.layout.fragment_search_result) {

    private val searchListAdapter =
        MultiTypePagingAdapter(ArticleDiffCalculator.getCommonArticleDiffItemCallback()).apply {
            register(HomeArticleItemBinderV2(this@SearchListFragment::onArticleClick))
        }

    @Inject
    lateinit var appViewModel: AppViewModel

    private val searchActivityViewModel by activityViewModels<SearchViewModel>()

    override val viewModel: SearchListViewModel by viewModels()

    override fun init(savedInstanceState: Bundle?) {
        initView()
        initEvent()
    }

    private fun initView() {
        viewBinding.searchResultList.apply {
            adapter = searchListAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }

    private fun initEvent() {
        lifecycleScope.launchWhenCreated {
            searchActivityViewModel.pagingDataFlow.collectLatest(searchListAdapter::submitData)
        }

        lifecycleScope.launchWhenCreated {
            searchListAdapter.loadStateFlow.collect { loadState ->
                viewBinding.loadingProgress.isVisible =
                    loadState.source.refresh is LoadState.Loading
                if (loadState.refresh == LoadState.Loading) {
                    viewBinding.searchResultList.scrollToPosition(0)
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
                articleAction.article.link
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
        }
    }
}