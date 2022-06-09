package com.lowe.wanandroid.ui.home.child.explore

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import com.lowe.wanandroid.MainViewModel
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.FragmentHomeChildExploreBinding
import com.lowe.wanandroid.services.model.Banner
import com.lowe.wanandroid.ui.BaseFragment
import com.lowe.wanandroid.ui.home.child.explore.repository.ExploreViewModel
import com.lowe.wanandroid.ui.home.item.HomeArticleItemBinder
import com.lowe.wanandroid.ui.home.item.HomeBannerItemBinder
import com.lowe.wanandroid.utils.ToastEx.showShortToast
import com.lowe.wanandroid.utils.loadMore

class ExploreFragment : BaseFragment<ExploreViewModel, FragmentHomeChildExploreBinding>(R.layout.fragment_home_child_explore) {

    companion object {
        private const val KEY_HOME_FRAGMENT_LIST_SAVE_STATE = "key_home_fragment_list_save_state"

        fun newInstance() = ExploreFragment()

    }

    private val homeAdapter = MultiTypeAdapter()
    private val mainViewModel by activityViewModels<MainViewModel>()

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
        homeAdapter.register(HomeArticleItemBinder())
        homeAdapter.register(HomeBannerItemBinder(this::onBannerItemClick))
        viewBinding.homeList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = homeAdapter
            loadMore(loadFinish = { viewModel.isLoading.not() }) {
                viewModel.fetchArticleList()
            }
        }
        viewBinding.swipeRefreshLayout.setOnRefreshListener {
            onRefresh()
        }
    }

    private fun initObserve() {
        viewModel.apply {
            articleListLiveData.observe(viewLifecycleOwner) {
                it?.let(this@ExploreFragment::afterLoadArticle)
            }
        }
        mainViewModel.apply {
            mainTabDoubleClickLiveData.observe(viewLifecycleOwner) {
                if (it == this@ExploreFragment.tag) {
                    scrollToTop()
                }
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
        viewBinding.swipeRefreshLayout.isRefreshing = true
        viewModel.refreshArticleList()
        viewBinding.swipeRefreshLayout.isRefreshing = false
    }

    private fun onBannerItemClick(data: Banner, position: Int) {
        position.toString().showShortToast()
    }

}