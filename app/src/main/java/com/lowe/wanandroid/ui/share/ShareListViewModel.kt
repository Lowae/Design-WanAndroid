package com.lowe.wanandroid.ui.share

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.lowe.wanandroid.account.IAccountViewModelDelegate
import com.lowe.wanandroid.ui.BaseViewModel
import com.lowe.wanandroid.utils.tryOffer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class ShareListViewModel @Inject constructor(
    private val repository: ShareListRepository,
    private val accountViewModelDelegate: IAccountViewModelDelegate
) :
    BaseViewModel(), IAccountViewModelDelegate by accountViewModelDelegate {

    private val _fetchShare = Channel<String>(Channel.CONFLATED)

    /**
     * ShareBean数据流
     */
    val getShareBeanFlow = repository.shareBeanFlow

    /**
     * 分享列表数据流
     */
    val shareListFlow = _fetchShare.receiveAsFlow().flatMapLatest {
        repository.getShareList(it, it == userId)
    }.cachedIn(viewModelScope)

    fun fetch(userId: String) {
        _fetchShare.tryOffer(userId)
    }
}