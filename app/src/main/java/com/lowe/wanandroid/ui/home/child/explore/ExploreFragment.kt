package com.lowe.wanandroid.ui.home.child.explore

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.lowe.multitype.MultiTypePagingAdapter
import com.lowe.wanandroid.R
import com.lowe.wanandroid.base.app.AppViewModel
import com.lowe.wanandroid.compat.BundleCompat
import com.lowe.wanandroid.databinding.FragmentHomeChildExploreBinding
import com.lowe.wanandroid.services.model.Article
import com.lowe.wanandroid.services.model.Banner
import com.lowe.wanandroid.services.model.CollectEvent
import com.lowe.wanandroid.ui.ArticleDiffCalculator
import com.lowe.wanandroid.ui.BaseFragment
import com.lowe.wanandroid.ui.LoadMoreItemBinder
import com.lowe.wanandroid.ui.home.HomeChildFragmentAdapter
import com.lowe.wanandroid.ui.home.HomeFragment
import com.lowe.wanandroid.ui.home.HomeTabBean
import com.lowe.wanandroid.ui.home.HomeViewModel
import com.lowe.wanandroid.ui.home.item.ArticleAction
import com.lowe.wanandroid.ui.home.item.HomeArticleItemBinderV2
import com.lowe.wanandroid.ui.home.item.HomeBannerItemBinder
import com.lowe.wanandroid.ui.web.WebActivity
import com.lowe.wanandroid.utils.Activities
import com.lowe.wanandroid.utils.intentTo
import com.lowe.wanandroid.utils.isEmpty
import com.lowe.wanandroid.utils.isRefreshing
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

/**
 * 首页页面
 */
@AndroidEntryPoint
class ExploreFragment :
    BaseFragment<ExploreViewModel, FragmentHomeChildExploreBinding>(R.layout.fragment_home_child_explore) {

    companion object {
        private const val KEY_HOME_FRAGMENT_LIST_SAVE_STATE = "key_home_fragment_list_save_state"

        fun newInstance(homeTabBean: HomeTabBean): ExploreFragment = with(ExploreFragment()) {
            arguments = bundleOf(
                HomeFragment.KEY_CHILD_HOME_TAB_PARCELABLE to homeTabBean
            )
            this
        }
    }

    @Inject
    lateinit var appViewModel: AppViewModel

    private val homeAdapter =
        MultiTypePagingAdapter(ArticleDiffCalculator.getCommonDiffItemCallback()).apply {
            register(HomeBannerItemBinder(this@ExploreFragment::onBannerItemClick))
            register(HomeArticleItemBinderV2(this@ExploreFragment::onItemClick))
            registerFooter(LoadMoreItemBinder())
        }
    private val homeViewModel by viewModels<HomeViewModel>(this::requireParentFragment)
    private val exploreTabBean by lazy(LazyThreadSafetyMode.NONE) {
        BundleCompat.getParcelable(arguments, HomeFragment.KEY_CHILD_HOME_TAB_PARCELABLE)
            ?: HomeTabBean(HomeChildFragmentAdapter.HOME_TAB_EXPLORE)
    }

    override val viewModel: ExploreViewModel by viewModels()

    override fun onViewCreated(savedInstanceState: Bundle?) {
        initView()
        initEvents()
        if (savedInstanceState == null) {
            onRefresh()
        } else {
            viewDataBinding.homeList.layoutManager?.onRestoreInstanceState(
                BundleCompat.getParcelable(
                    savedInstanceState,
                    KEY_HOME_FRAGMENT_LIST_SAVE_STATE
                )
            )
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(
            KEY_HOME_FRAGMENT_LIST_SAVE_STATE,
            viewDataBinding.homeList.layoutManager?.onSaveInstanceState()
        )
    }

    private fun initView() {
        viewDataBinding.homeList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = homeAdapter
            setHasFixedSize(true)
        }
    }

    private fun initEvents() {
        lifecycleScope.launchWhenCreated {
            viewModel.getArticlesFlow().collectLatest(homeAdapter::submitData)
        }
        lifecycleScope.launchWhenCreated {
            homeAdapter.loadStateFlow.collect(this@ExploreFragment::updateLoadStates)
        }
        homeViewModel.apply {
            scrollToTopLiveData.observe(viewLifecycleOwner) {
                if (it.title == exploreTabBean.title) scrollToTop()
            }
            refreshLiveData.observe(viewLifecycleOwner) {
                if (it.title == exploreTabBean.title) onRefresh()
            }
        }
        appViewModel.collectArticleEvent.observe(viewLifecycleOwner) { event ->
            homeAdapter.snapshot().run {
                val index = indexOfFirst { it is Article && it.id == event.id }
                if (index >= 0) {
                    (this[index] as? Article)?.collect = event.isCollected
                    index
                } else null
            }?.apply(homeAdapter::notifyItemChanged)
        }
    }

    private fun scrollToTop() {
        viewDataBinding.homeList.scrollToPosition(0)
    }

    private fun onRefresh() {
        homeAdapter.refresh()
    }

    private fun onBannerItemClick(data: Banner, position: Int) {
        WebActivity.loadUrl(this.requireContext(), data.url)
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
                startActivity(intentTo(Activities.ShareList(bundle = bundleOf(Activities.ShareList.KEY_SHARE_LIST_USER_ID to articleAction.article.userId.toString()))))
            }
        }
    }

    private fun updateLoadStates(loadStates: CombinedLoadStates) {
        viewDataBinding.loadingContainer.apply {
            emptyLayout.isVisible =
                loadStates.refresh is LoadState.NotLoading && homeAdapter.isEmpty()
            loadingProgress.isVisible = homeAdapter.isEmpty() && loadStates.isRefreshing
        }
    }
}