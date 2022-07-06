package com.lowe.wanandroid.ui.coin

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.lowe.multitype.PagingLoadStateAdapter
import com.lowe.multitype.PagingMultiTypeAdapter
import com.lowe.wanandroid.BR
import com.lowe.wanandroid.R
import com.lowe.wanandroid.base.SimpleDiffItemCallback
import com.lowe.wanandroid.base.http.exception.ApiException
import com.lowe.wanandroid.databinding.ActivityMyCoinInfoBinding
import com.lowe.wanandroid.services.model.CoinHistory
import com.lowe.wanandroid.services.model.UserBaseInfo
import com.lowe.wanandroid.ui.ActivityDataBindingDelegate
import com.lowe.wanandroid.ui.BaseActivity
import com.lowe.wanandroid.ui.SimpleFooterItemBinder
import com.lowe.wanandroid.ui.coin.item.CoinHistoryItemBinder
import com.lowe.wanandroid.ui.coin.ranking.CoinRankingActivity
import com.lowe.wanandroid.ui.web.WebActivity
import com.lowe.wanandroid.utils.isEmpty
import com.lowe.wanandroid.utils.isRefreshing
import com.lowe.wanandroid.utils.showShortToast
import com.lowe.wanandroid.utils.whenError
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest

/**
 * 我的积分记录页面
 */
@AndroidEntryPoint
class MyCoinInfoActivity : BaseActivity<MyCoinInfoViewModel, ActivityMyCoinInfoBinding>() {

    companion object {
        val COIN_DIFF_CALLBACK =
            SimpleDiffItemCallback<CoinHistory>(
                { old, new -> old.id == new.id },
                { old, new -> old == new }
            )
    }

    private val coinHistoryAdapter = PagingMultiTypeAdapter(COIN_DIFF_CALLBACK).apply {
        register(CoinHistoryItemBinder())
    }

    override val viewDataBinding: ActivityMyCoinInfoBinding by ActivityDataBindingDelegate(
        R.layout.activity_my_coin_info
    )
    override val viewModel: MyCoinInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initEvent()
    }

    private fun initView() {
        with(viewDataBinding.coinList) {
            adapter = coinHistoryAdapter.withLoadStateFooter(
                PagingLoadStateAdapter(
                    SimpleFooterItemBinder(),
                    coinHistoryAdapter.types
                )
            )
            setHasFixedSize(true)
        }
        with(viewDataBinding.toolbar) {
            setNavigationOnClickListener { finish() }
        }
        viewDataBinding.coinRulesHelp.setOnClickListener {
            WebActivity.loadUrl(this, "https://www.wanandroid.com/blog/show/2653")
        }
        viewDataBinding.coinRanking.setOnClickListener {
            startActivity(Intent(this, CoinRankingActivity::class.java))
        }
    }

    private fun initEvent() {
        lifecycleScope.launchWhenCreated {
            coinHistoryAdapter.loadStateFlow.collect(this@MyCoinInfoActivity::updateLoadStates)
        }
        lifecycleScope.launchWhenCreated {
            viewModel.userBaseInfoFlow.collectLatest(this@MyCoinInfoActivity::updateUserCoinInfo)
        }
        lifecycleScope.launchWhenCreated {
            viewModel.coinHistoryFlow.collectLatest(coinHistoryAdapter::submitData)
        }
    }

    private fun updateUserCoinInfo(info: UserBaseInfo) {
        viewDataBinding.userInfo = info
        viewDataBinding.notifyPropertyChanged(BR.userInfo)
    }

    private fun updateLoadStates(loadStates: CombinedLoadStates) {
        loadStates.whenError {
            (it.error as? ApiException)?.message?.showShortToast()
        }
        viewDataBinding.loadingContainer.apply {
            emptyLayout.isVisible =
                loadStates.refresh is LoadState.NotLoading && coinHistoryAdapter.isEmpty()
            loadingProgress.isVisible = coinHistoryAdapter.isEmpty() && loadStates.isRefreshing
        }
    }
}