package com.lowe.wanandroid.ui.coin

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.lowe.wanandroid.account.AccountManager
import com.lowe.wanandroid.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyCoinInfoViewModel @Inject constructor(repository: CoinRepository) :
    BaseViewModel() {

    val userBaseInfoFlow = AccountManager.collectUserInfoFlow()

    val coinHistoryFlow = repository.getCoinHistoryFlow().cachedIn(viewModelScope)

}