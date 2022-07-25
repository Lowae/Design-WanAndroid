package com.lowe.wanandroid.ui.coin.ranking

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.lowe.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CoinRankingViewModel @Inject constructor(repository: CoinRankingRepository) :
    BaseViewModel() {

    val coinRankingFlow = repository.coinRankingFlow.cachedIn(viewModelScope)

}