package com.lowe.wanandroid.ui.navigator.child.series.detail.child

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.lowe.multitype.paging.MultiTypePagingAdapter
import com.lowe.wanandroid.R
import com.lowe.wanandroid.base.app.AppViewModel
import com.lowe.wanandroid.databinding.FragmentSeriesDetailChildBinding
import com.lowe.wanandroid.services.model.Article
import com.lowe.wanandroid.services.model.Classify
import com.lowe.wanandroid.services.model.CollectEvent
import com.lowe.wanandroid.ui.ArticleDiffCalculator
import com.lowe.wanandroid.ui.BaseFragment
import com.lowe.wanandroid.ui.home.item.ArticleAction
import com.lowe.wanandroid.ui.home.item.HomeArticleItemBinderV2
import com.lowe.wanandroid.ui.navigator.child.series.detail.SeriesDetailListViewModel
import com.lowe.wanandroid.ui.web.WebActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class SeriesDetailChildFragment :
    BaseFragment<SeriesDetailChildViewModel, FragmentSeriesDetailChildBinding>(R.layout.fragment_series_detail_child) {

    companion object {

        const val KEY_SERIES_DETAIL_CHILD_TAB = "key_series_detail_child_tab"

        fun newInstance(classify: Classify) =
            with(SeriesDetailChildFragment()) {
                arguments = bundleOf(KEY_SERIES_DETAIL_CHILD_TAB to classify)
                this
            }
    }

    @Inject
    lateinit var appViewModel: AppViewModel

    private val classify by lazy(LazyThreadSafetyMode.NONE) {
        arguments?.getParcelable(KEY_SERIES_DETAIL_CHILD_TAB) ?: Classify()
    }
    private val detailsAdapter =
        MultiTypePagingAdapter(ArticleDiffCalculator.getCommonArticleDiffItemCallback()).apply {
            register(HomeArticleItemBinderV2(this@SeriesDetailChildFragment::onItemClick))
        }
    private val seriesDetailViewModel by activityViewModels<SeriesDetailListViewModel>()

    override val viewModel: SeriesDetailChildViewModel by viewModels()

    override fun init(savedInstanceState: Bundle?) {
        initView()
        initEvents()
    }

    private fun initView() {
        viewBinding.apply {
            with(seriesDetailList) {
                setHasFixedSize(true)
                adapter = detailsAdapter
                layoutManager = LinearLayoutManager(context)
            }
        }
    }

    private fun initEvents() {
        lifecycleScope.launchWhenCreated {
            viewModel.getSeriesDetailListFlow(classify.id).collectLatest(detailsAdapter::submitData)
        }
        seriesDetailViewModel.onRefreshLiveData.observe(viewLifecycleOwner) {
            if (it.id == classify.id) detailsAdapter.refresh()
        }
        appViewModel.collectArticleEvent.observe(viewLifecycleOwner) { event ->
            detailsAdapter.snapshot().run {
                val index = indexOfFirst { it is Article && it.id == event.id }
                if (index >= 0) {
                    (this[index] as? Article)?.collect = event.isCollected
                    index
                } else null
            }?.apply(detailsAdapter::notifyItemChanged)
        }
    }

    private fun onItemClick(articleAction: ArticleAction) {
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