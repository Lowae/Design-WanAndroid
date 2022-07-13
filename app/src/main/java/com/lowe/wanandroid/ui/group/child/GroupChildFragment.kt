package com.lowe.wanandroid.ui.group.child

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.lowe.multitype.PagingLoadStateAdapter
import com.lowe.multitype.PagingMultiTypeAdapter
import com.lowe.wanandroid.R
import com.lowe.wanandroid.base.app.AppViewModel
import com.lowe.wanandroid.databinding.FragmentChildGroupBinding
import com.lowe.wanandroid.services.model.Article
import com.lowe.wanandroid.services.model.CollectEvent
import com.lowe.wanandroid.ui.ArticleDiffCalculator
import com.lowe.wanandroid.ui.BaseFragment
import com.lowe.wanandroid.ui.SimpleFooterItemBinder
import com.lowe.wanandroid.ui.group.GroupViewModel
import com.lowe.wanandroid.ui.home.item.ArticleAction
import com.lowe.wanandroid.ui.home.item.HomeArticleItemBinderV2
import com.lowe.wanandroid.ui.web.WebActivity
import com.lowe.wanandroid.utils.Activities
import com.lowe.wanandroid.utils.isEmpty
import com.lowe.wanandroid.utils.isRefreshing
import com.lowe.wanandroid.utils.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 公众号TabLayout下的子Fragment
 */
@AndroidEntryPoint
class GroupChildFragment :
    BaseFragment<GroupChildViewModel, FragmentChildGroupBinding>(R.layout.fragment_child_group) {

    companion object {

        const val KEY_BUNDLE_GROUP_CHILD_ID = "key_bundle_group_child_id"

        fun newInstance(id: Int) =
            with(GroupChildFragment()) {
                arguments = bundleOf(
                    KEY_BUNDLE_GROUP_CHILD_ID to id
                )
                this
            }
    }

    @Inject
    lateinit var appViewModel: AppViewModel

    private val authorId by lazy(LazyThreadSafetyMode.NONE) {
        arguments?.getInt(
            KEY_BUNDLE_GROUP_CHILD_ID, 0
        ) ?: 0
    }

    private val articlesAdapter =
        PagingMultiTypeAdapter(ArticleDiffCalculator.getCommonDiffItemCallback()).apply {
            register(HomeArticleItemBinderV2(this@GroupChildFragment::onArticleClick))
        }
    private val groupViewModel by viewModels<GroupViewModel>(this::requireParentFragment)

    override val viewModel: GroupChildViewModel by viewModels()

    override fun onViewCreated(savedInstanceState: Bundle?) {
        initView()
        initEvents()
    }

    private fun initView() {
        viewDataBinding.apply {
            with(childList) {
                adapter = articlesAdapter.withLoadStateFooter(
                    PagingLoadStateAdapter(
                        SimpleFooterItemBinder(),
                        articlesAdapter.types
                    )
                )
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
            }
        }
    }

    private fun initEvents() {
        repeatOnStarted {
            launch {
                viewModel.getGroupArticlesFlow(authorId).collectLatest(articlesAdapter::submitData)
            }
            launch {
                articlesAdapter.loadStateFlow.collect(this@GroupChildFragment::updateLoadStates)
            }
        }
        groupViewModel.apply {
            scrollToTopLiveData.observe(viewLifecycleOwner) {
                if (it == authorId) scrollToTop()
            }
            parentRefreshLiveData.observe(viewLifecycleOwner) {
                if (it == authorId) articlesAdapter.refresh()
            }
        }
        appViewModel.collectArticleEvent.observe(viewLifecycleOwner) { event ->
            articlesAdapter.snapshot().run {
                val index = indexOfFirst { it is Article && it.id == event.id }
                if (index >= 0) {
                    (this[index] as? Article)?.collect = event.isCollected
                    index
                } else null
            }?.apply(articlesAdapter::notifyItemChanged)
        }
    }

    private fun onArticleClick(articleAction: ArticleAction) {
        when (articleAction) {
            is ArticleAction.ItemClick -> WebActivity.loadUrl(
                requireContext(),
                Activities.Web.WebIntent(
                    articleAction.article.link,
                    articleAction.article.id,
                    articleAction.article.collect
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

    private fun scrollToTop() {
        viewDataBinding.childList.scrollToPosition(0)
    }

    private fun updateLoadStates(loadStates: CombinedLoadStates) {
        viewDataBinding.loadingContainer.apply {
            emptyLayout.isVisible =
                loadStates.refresh is LoadState.NotLoading && articlesAdapter.isEmpty()
            loadingProgress.isVisible = articlesAdapter.isEmpty() && loadStates.isRefreshing
        }
    }
}
