package com.lowe.wanandroid.ui.home.child.explore

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.FragmentHomeChildExploreBinding
import com.lowe.wanandroid.services.model.Article
import com.lowe.wanandroid.services.model.Banner
import com.lowe.wanandroid.ui.BaseFragment
import com.lowe.wanandroid.ui.home.HomeChildFragmentAdapter
import com.lowe.wanandroid.ui.home.HomeFragment
import com.lowe.wanandroid.ui.home.HomeTabBean
import com.lowe.wanandroid.ui.home.HomeViewModel
import com.lowe.wanandroid.ui.home.child.explore.repository.ExploreViewModel
import com.lowe.wanandroid.ui.home.item.HomeArticleItemBinder
import com.lowe.wanandroid.ui.home.item.HomeBannerItemBinder
import com.lowe.wanandroid.ui.web.WebActivity
import com.lowe.wanandroid.utils.loadMore

class ExploreFragment :
    BaseFragment<ExploreViewModel, FragmentHomeChildExploreBinding>(R.layout.fragment_home_child_explore) {

    companion object {
        private const val KEY_HOME_FRAGMENT_LIST_SAVE_STATE = "key_home_fragment_list_save_state"

        fun newInstance(homeTabBean: HomeTabBean): ExploreFragment = with(ExploreFragment()) {
            arguments?.apply {
                putParcelable(HomeFragment.KEY_CHILD_HOME_TAB_PARCELABLE, homeTabBean)
            }
            this
        }

    }

    private val homeAdapter = MultiTypeAdapter()
    private val homeViewModel by viewModels<HomeViewModel>(this::requireParentFragment)
    private val exploreTabBean by lazy(LazyThreadSafetyMode.NONE) {
        arguments?.getParcelable(HomeFragment.KEY_CHILD_HOME_TAB_PARCELABLE) ?: HomeTabBean(
            HomeChildFragmentAdapter.HOME_TAB_EXPLORE
        )
    }

    override fun createViewModel() = ExploreViewModel()

    override fun init(savedInstanceState: Bundle?) {
        initView()
        initObserve()
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
        homeAdapter.register(HomeArticleItemBinder(this::onItemClick))
        homeAdapter.register(HomeBannerItemBinder(this::onBannerItemClick))
        viewBinding.homeList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = homeAdapter
            loadMore(loadFinish = { viewModel.isLoading.not() }) {
                viewModel.fetchArticleList()
            }
        }
    }

    private fun initObserve() {
        viewModel.apply {
            articleListLiveData.observe(viewLifecycleOwner) {
                it?.let(this@ExploreFragment::afterLoadArticle)
            }
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

    private fun afterLoadArticle(result: Pair<List<Any>, DiffUtil.DiffResult>) {
        homeAdapter.items = result.first
        result.second.dispatchUpdatesTo(homeAdapter)
    }

    private fun onRefresh() {
        viewModel.refreshArticleList()
    }

    private fun onBannerItemClick(data: Banner, position: Int) {
        WebActivity.loadUrl(this.requireContext(), data.url)
    }

    private fun onItemClick(action: Pair<Int, Article>) {
        val (position, article) = action
        WebActivity.loadUrl(this.requireContext(), article.link)
    }
}