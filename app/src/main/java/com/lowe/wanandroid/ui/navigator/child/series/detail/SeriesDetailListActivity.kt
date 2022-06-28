package com.lowe.wanandroid.ui.navigator.child.series.detail

import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.ActivitySeriesDetailListLayoutBinding
import com.lowe.wanandroid.services.model.Classify
import com.lowe.wanandroid.ui.ActivityDataBindingDelegate
import com.lowe.wanandroid.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeriesDetailListActivity :
    BaseActivity<SeriesDetailListViewModel, ActivitySeriesDetailListLayoutBinding>() {

    companion object {

        const val KEY_BUNDLE_CLASSIFY_LIST_TAB = "key_bundle_classify_list_Tab"
        const val KEY_BUNDLE_INIT_TAB_INDEX = "key_bundle_init_tab_index"

    }

    private lateinit var detailFragmentAdapter: SeriesDetailFragmentStateAdapter
    private lateinit var tabLayoutMediator: TabLayoutMediator

    private val classifyList: List<Classify> by lazy(LazyThreadSafetyMode.NONE) {
        intent.getParcelableArrayListExtra(
            KEY_BUNDLE_CLASSIFY_LIST_TAB
        ) ?: emptyList()
    }

    private val initIndex: Int by lazy(LazyThreadSafetyMode.NONE) {
        intent.getIntExtra(KEY_BUNDLE_INIT_TAB_INDEX, -1)
    }

    override val viewDataBinding: ActivitySeriesDetailListLayoutBinding by ActivityDataBindingDelegate(
        R.layout.activity_series_detail_list_layout
    )

    override val viewModel: SeriesDetailListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailFragmentAdapter =
            SeriesDetailFragmentStateAdapter(classifyList, supportFragmentManager, lifecycle)
        initView()
        initObserve()
    }

    private fun initView() {
        viewDataBinding.apply {
            with(seriesDetailPager2) {
                adapter = detailFragmentAdapter
                setCurrentItem(initIndex.takeIf { it >= 0 } ?: 0, false)
            }
            tabLayoutMediator = TabLayoutMediator(
                seriesDetailTabLayout,
                seriesDetailPager2
            ) { tab: TabLayout.Tab, position: Int ->
                tab.text = detailFragmentAdapter.items[position].name
            }.apply(TabLayoutMediator::attach)
        }
    }

    private fun initObserve() {
        viewDataBinding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.onRefreshLiveData.value =
                detailFragmentAdapter.items[viewDataBinding.seriesDetailPager2.currentItem]
            viewDataBinding.swipeRefreshLayout.isRefreshing = false
        }
    }
}