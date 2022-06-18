package com.lowe.wanandroid.ui.home.child.explore

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.lowe.multitype.paging.MultiTypePagingAdapter
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.FragmentHomeChildExploreBinding
import com.lowe.wanandroid.services.model.Article
import com.lowe.wanandroid.services.model.Banner
import com.lowe.wanandroid.ui.ArticleDiffCalculator
import com.lowe.wanandroid.ui.BaseFragment
import com.lowe.wanandroid.ui.home.HomeChildFragmentAdapter
import com.lowe.wanandroid.ui.home.HomeFragment
import com.lowe.wanandroid.ui.home.HomeTabBean
import com.lowe.wanandroid.ui.home.HomeViewModel
import com.lowe.wanandroid.ui.home.item.HomeArticleItemBinderV2
import com.lowe.wanandroid.ui.home.item.HomeBannerItemBinder
import com.lowe.wanandroid.ui.web.WebActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

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

    private val homeAdapter =
        MultiTypePagingAdapter(ArticleDiffCalculator.getCommonArticleDiffItemCallback()).apply {
            register(HomeBannerItemBinder(this@ExploreFragment::onBannerItemClick))
            register(HomeArticleItemBinderV2(this@ExploreFragment::onItemClick))
        }
    private val homeViewModel by viewModels<HomeViewModel>(this::requireParentFragment)
    private val exploreTabBean by lazy(LazyThreadSafetyMode.NONE) {
        arguments?.getParcelable(HomeFragment.KEY_CHILD_HOME_TAB_PARCELABLE) ?: HomeTabBean(
            HomeChildFragmentAdapter.HOME_TAB_EXPLORE
        )
    }

    override val viewModel: ExploreViewModel by viewModels()

    override fun init(savedInstanceState: Bundle?) {
        initView()
        initEvents()
        if (savedInstanceState == null) {
            onRefresh()
        } else {
            viewBinding.homeList.layoutManager?.onRestoreInstanceState(
                savedInstanceState.getParcelable(KEY_HOME_FRAGMENT_LIST_SAVE_STATE)
            )
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(
            KEY_HOME_FRAGMENT_LIST_SAVE_STATE,
            viewBinding.homeList.layoutManager?.onSaveInstanceState()
        )
    }

    private fun initView() {
        viewBinding.homeList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = homeAdapter
            setHasFixedSize(true)
        }
    }

    private fun initEvents() {
        lifecycleScope.launchWhenCreated {
            viewModel.getArticlesFlow().collectLatest(homeAdapter::submitData)
        }
        homeViewModel.apply {
            scrollToTopLiveData.observe(viewLifecycleOwner) {
                if (it.title == exploreTabBean.title) scrollToTop()
            }
            refreshLiveData.observe(viewLifecycleOwner) {
                if (it.title == exploreTabBean.title) onRefresh()
            }
        }
    }

    private fun scrollToTop() {
        viewBinding.homeList.scrollToPosition(0)
    }

    private fun onRefresh() {
        homeAdapter.refresh()
    }

    private fun onBannerItemClick(data: Banner, position: Int) {
        WebActivity.loadUrl(this.requireContext(), data.url)
    }

    private fun onItemClick(position: Int, article: Article) {
        WebActivity.loadUrl(this.requireContext(), article.link)
    }
}