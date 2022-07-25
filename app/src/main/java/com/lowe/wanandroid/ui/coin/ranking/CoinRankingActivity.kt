package com.lowe.wanandroid.ui.coin.ranking

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.lowe.common.base.SimpleDiffItemCallback
import com.lowe.common.services.model.CoinInfo
import com.lowe.common.utils.isEmpty
import com.lowe.common.utils.isRefreshing
import com.lowe.common.utils.launchRepeatOnStarted
import com.lowe.multitype.PagingLoadStateAdapter
import com.lowe.multitype.PagingMultiTypeAdapter
import com.lowe.wanandroid.BaseActivity
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.ActivityCoinRankingBinding
import com.lowe.wanandroid.ui.ActivityDataBindingDelegate
import com.lowe.wanandroid.ui.SimpleFooterItemBinder
import com.lowe.wanandroid.ui.coin.ranking.item.CoinInfoItemBinder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CoinRankingActivity : BaseActivity<CoinRankingViewModel, ActivityCoinRankingBinding>() {

    companion object {
        private val COIN_DIFF_ITEM_CALLBACK =
            SimpleDiffItemCallback<CoinInfo>(
                { old, new -> old.userId == new.userId },
                { old, new -> old == new }
            )
    }

    private val rankingAdapter = PagingMultiTypeAdapter(COIN_DIFF_ITEM_CALLBACK).apply {
        register(CoinInfoItemBinder())
    }

    override val viewDataBinding: ActivityCoinRankingBinding by ActivityDataBindingDelegate(R.layout.activity_coin_ranking)

    override val viewModel: CoinRankingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initEvent()
    }

    private fun initView() {
        with(viewDataBinding.rankList) {
            adapter = rankingAdapter.withLoadStateFooter(
                PagingLoadStateAdapter(
                    SimpleFooterItemBinder(),
                    rankingAdapter.types
                )
            )
            setHasFixedSize(true)
        }
        viewDataBinding.toolbar.setNavigationOnClickListener { finish() }
    }

    private fun initEvent() {
        launchRepeatOnStarted {
            launch {
                viewModel.coinRankingFlow.collectLatest(rankingAdapter::submitData)
            }
            launch {
                rankingAdapter.loadStateFlow.collectLatest(this@CoinRankingActivity::updateLoadStates)
            }
        }
    }

    private fun updateLoadStates(loadStates: CombinedLoadStates) {
        viewDataBinding.loadingContainer.apply {
            emptyLayout.isVisible =
                loadStates.refresh is LoadState.NotLoading && rankingAdapter.isEmpty()
            loadingProgress.isVisible = rankingAdapter.isEmpty() && loadStates.isRefreshing
        }
    }
}