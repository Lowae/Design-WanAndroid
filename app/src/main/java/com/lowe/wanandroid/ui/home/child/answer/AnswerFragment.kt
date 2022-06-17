package com.lowe.wanandroid.ui.home.child.answer

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.lowe.multitype.MultiTypeAdapter
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.FragmentHomeChildAnswerBinding
import com.lowe.wanandroid.services.model.Article
import com.lowe.wanandroid.ui.BaseFragment
import com.lowe.wanandroid.ui.home.HomeChildFragmentAdapter
import com.lowe.wanandroid.ui.home.HomeFragment
import com.lowe.wanandroid.ui.home.HomeTabBean
import com.lowe.wanandroid.ui.home.HomeViewModel
import com.lowe.wanandroid.ui.home.child.answer.repository.AnswerViewModel
import com.lowe.wanandroid.ui.home.item.HomeArticleItemBinder
import com.lowe.wanandroid.ui.web.WebActivity
import com.lowe.wanandroid.utils.loadMore

class AnswerFragment :
    BaseFragment<AnswerViewModel, FragmentHomeChildAnswerBinding>(R.layout.fragment_home_child_answer) {

    companion object {
        fun newInstance(homeTabBean: HomeTabBean) = with(AnswerFragment()) {
            arguments = bundleOf(
                HomeFragment.KEY_CHILD_HOME_TAB_PARCELABLE to homeTabBean
            )
            this
        }
    }

    private val homeViewModel by viewModels<HomeViewModel>(this::requireParentFragment)
    private val squareTabBean by lazy(LazyThreadSafetyMode.NONE) {
        arguments?.getParcelable(HomeFragment.KEY_CHILD_HOME_TAB_PARCELABLE) ?: HomeTabBean(
            HomeChildFragmentAdapter.HOME_TAB_ANSWER
        )
    }
    private val squareAdapter = MultiTypeAdapter()

    override val viewModel: AnswerViewModel by viewModels()

    override fun init(savedInstanceState: Bundle?) {
        initView()
        initObserve()
    }

    private fun initView() {
        squareAdapter.register(HomeArticleItemBinder(this::onItemClick))
        viewBinding.apply {
            with(answerList) {
                layoutManager = LinearLayoutManager(context)
                adapter = squareAdapter
                loadMore(loadFinish = { this@AnswerFragment.viewModel.isLoading.not() }) {
                    this@AnswerFragment.viewModel.fetchAnswerList()
                }
            }
        }
    }

    private fun initObserve() {
        viewModel.apply {
            this.answerListLiveData.observe(
                viewLifecycleOwner,
                this@AnswerFragment::dispatchToAdapter
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
        viewBinding.answerList.scrollToPosition(0)
    }

    private fun onRefresh() {
        viewModel.fetchAnswerList(true)
    }

    private fun dispatchToAdapter(result: Pair<List<Any>, DiffUtil.DiffResult>) {
        squareAdapter.items = result.first
        result.second.dispatchUpdatesTo(squareAdapter)
    }

    private fun onItemClick(position: Int, article: Article) {
        WebActivity.loadUrl(this.requireContext(), article.link)
    }

}