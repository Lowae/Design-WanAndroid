package com.lowe.wanandroid.ui.navigator.child.series.detail.child

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.FragmentSeriesDetailChildBinding
import com.lowe.wanandroid.services.model.Article
import com.lowe.wanandroid.services.model.Classify
import com.lowe.wanandroid.ui.BaseFragment
import com.lowe.wanandroid.ui.home.item.HomeArticleItemBinder
import com.lowe.wanandroid.ui.navigator.child.series.detail.SeriesDetailListViewModel
import com.lowe.wanandroid.ui.web.WebActivity
import com.lowe.wanandroid.utils.loadMore

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

    private val classify by lazy(LazyThreadSafetyMode.NONE) {
        arguments?.getParcelable(KEY_SERIES_DETAIL_CHILD_TAB) ?: Classify()
    }
    private val detailsAdapter = MultiTypeAdapter()
    private val seriesDetailViewModel by activityViewModels<SeriesDetailListViewModel>()

    override val viewModel: SeriesDetailChildViewModel by viewModels()

    override fun init(savedInstanceState: Bundle?) {
        initView()
        initObserve()
        onRefresh()
    }

    private fun initView() {
        viewBinding.apply {
            with(seriesDetailList) {
                detailsAdapter.register(HomeArticleItemBinder(this@SeriesDetailChildFragment::onItemClick))
                setHasFixedSize(true)
                adapter = detailsAdapter
                layoutManager = LinearLayoutManager(context)
            }
        }
    }

    private fun initObserve() {
        viewModel.apply {
            detailListLiveData.observe(
                viewLifecycleOwner,
                this@SeriesDetailChildFragment::dispatchToAdapter
            )
            viewBinding.seriesDetailList.loadMore(loadFinish = { isLoading.not() && hasMore }) {
                this.fetchSeriesDetailList(classify.id)
            }
        }
        seriesDetailViewModel.apply {
            onRefreshLiveData.observe(viewLifecycleOwner) {
                if (it.id == classify.id) viewModel.fetchSeriesDetailList(classify.id, true)
            }
        }
    }


    private fun onRefresh() {
        viewModel.fetchSeriesDetailList(classify.id)
    }

    private fun dispatchToAdapter(result: Pair<List<Any>, DiffUtil.DiffResult>) {
        detailsAdapter.items = result.first
        result.second.dispatchUpdatesTo(detailsAdapter)
    }

    private fun onItemClick(position: Int, article: Article) {
        WebActivity.loadUrl(this.requireContext(), article.link)
    }
}