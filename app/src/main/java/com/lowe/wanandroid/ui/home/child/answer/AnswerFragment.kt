package com.lowe.wanandroid.ui.home.child.answer

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
import com.lowe.wanandroid.databinding.FragmentHomeChildAnswerBinding
import com.lowe.wanandroid.services.model.Article
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
import com.lowe.wanandroid.ui.web.WebActivity
import com.lowe.wanandroid.utils.Activities
import com.lowe.wanandroid.utils.isEmpty
import com.lowe.wanandroid.utils.isRefreshing
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

/**
 * 问答页面
 */
@AndroidEntryPoint
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

    @Inject
    lateinit var appViewModel: AppViewModel

    private val homeViewModel by viewModels<HomeViewModel>(this::requireParentFragment)
    private val squareTabBean by lazy(LazyThreadSafetyMode.NONE) {
        BundleCompat.getParcelable(arguments, HomeFragment.KEY_CHILD_HOME_TAB_PARCELABLE)
            ?: HomeTabBean(HomeChildFragmentAdapter.HOME_TAB_ANSWER)
    }
    private val answerAdapter =
        MultiTypePagingAdapter(ArticleDiffCalculator.getCommonDiffItemCallback()).apply {
            register(HomeArticleItemBinderV2(this@AnswerFragment::onItemClick))
            registerFooter(LoadMoreItemBinder())
        }
    override val viewModel: AnswerViewModel by viewModels()

    override fun onViewCreated(savedInstanceState: Bundle?) {
        initView()
        initEvents()
    }

    private fun initView() {
        viewDataBinding.apply {
            with(answerList) {
                layoutManager = LinearLayoutManager(context)
                adapter = answerAdapter
                setHasFixedSize(true)
            }
        }
    }

    private fun initEvents() {
        lifecycleScope.launchWhenCreated {
            viewModel.getAnswerListFlow().collectLatest(answerAdapter::submitData)
        }
        lifecycleScope.launchWhenCreated {
            answerAdapter.loadStateFlow.collect(this@AnswerFragment::updateLoadStates)
        }
        homeViewModel.apply {
            scrollToTopLiveData.observe(viewLifecycleOwner) {
                if (it.title == squareTabBean.title) scrollToTop()
            }
            refreshLiveData.observe(viewLifecycleOwner) {
                if (it.title == squareTabBean.title) answerAdapter.refresh()
            }
        }
        appViewModel.collectArticleEvent.observe(viewLifecycleOwner) { event ->
            answerAdapter.snapshot().run {
                val index = indexOfFirst { it is Article && it.id == event.id }
                if (index >= 0) {
                    (this[index] as? Article)?.collect = event.isCollected
                    index
                } else null
            }?.apply(answerAdapter::notifyItemChanged)
        }
    }

    private fun scrollToTop() {
        viewDataBinding.answerList.scrollToPosition(0)
    }

    private fun onItemClick(articleAction: ArticleAction) {
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

    private fun updateLoadStates(loadStates: CombinedLoadStates) {
        viewDataBinding.loadingContainer.apply {
            emptyLayout.isVisible =
                loadStates.refresh is LoadState.NotLoading && answerAdapter.isEmpty()
            loadingProgress.isVisible = answerAdapter.isEmpty() && loadStates.isRefreshing
        }
    }
}