package com.lowe.wanandroid.ui.home.child.square

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.FragmentHomeChildSquareBinding
import com.lowe.wanandroid.services.model.Article
import com.lowe.wanandroid.ui.BaseFragment
import com.lowe.wanandroid.ui.home.HomeChildFragmentAdapter
import com.lowe.wanandroid.ui.home.HomeFragment
import com.lowe.wanandroid.ui.home.HomeTabBean
import com.lowe.wanandroid.ui.home.HomeViewModel
import com.lowe.wanandroid.ui.home.child.square.repository.SquareViewModel
import com.lowe.wanandroid.ui.home.item.HomeArticleItemBinder
import com.lowe.wanandroid.ui.web.WebActivity
import com.lowe.wanandroid.utils.loadMore

class SquareFragment :
    BaseFragment<SquareViewModel, FragmentHomeChildSquareBinding>(R.layout.fragment_home_child_square) {

    companion object {
        fun newInstance(homeTabBean: HomeTabBean) = with(SquareFragment()) {
            arguments = bundleOf(
                HomeFragment.KEY_CHILD_HOME_TAB_PARCELABLE to homeTabBean
            )
            this
        }
    }

    private val homeViewModel by viewModels<HomeViewModel>(this::requireParentFragment)
    private val squareTabBean by lazy(LazyThreadSafetyMode.NONE) {
        arguments?.getParcelable(HomeFragment.KEY_CHILD_HOME_TAB_PARCELABLE) ?: HomeTabBean(
            HomeChildFragmentAdapter.HOME_TAB_SQUARE
        )
    }
    private val squareAdapter = MultiTypeAdapter()

    override val viewModel: SquareViewModel by viewModels()

    override fun init(savedInstanceState: Bundle?) {
        initView()
        initObserve()
    }

    private fun initView() {
        squareAdapter.register(HomeArticleItemBinder(this::onItemClick))
        viewBinding.apply {
            with(squareList) {
                layoutManager = LinearLayoutManager(context)
                adapter = squareAdapter
                loadMore(loadFinish = { this@SquareFragment.viewModel.isLoading.not() }) {
                    this@SquareFragment.viewModel.fetchSquareList()
                }
            }
        }
    }

    private fun initObserve() {
        viewModel.apply {
            this.squareListLiveData.observe(
                viewLifecycleOwner,
                this@SquareFragment::dispatchToAdapter
            )
        }
        homeViewModel.apply {
            scrollToTopLiveData.observe(viewLifecycleOwner) {
                if (it.title == squareTabBean.title) scrollToTop()
            }
            refreshLiveData.observe(viewLifecycleOwner) {
                if (it.title == squareTabBean.title) onRefresh()
            }
        }
    }

    private fun scrollToTop() {
        viewBinding.squareList.scrollToPosition(0)
    }

    private fun onRefresh() {
        viewModel.fetchSquareList(true)
    }

    private fun dispatchToAdapter(result: Pair<List<Any>, DiffUtil.DiffResult>) {
        squareAdapter.items = result.first
        result.second.dispatchUpdatesTo(squareAdapter)
    }

    private fun onItemClick(action: Pair<Int, Article>) {
        val (position, article) = action
        WebActivity.loadUrl(this.requireContext(), article.link)
    }
}