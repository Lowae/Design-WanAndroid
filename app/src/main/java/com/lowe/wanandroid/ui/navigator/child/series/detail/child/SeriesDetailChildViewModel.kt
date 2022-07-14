package com.lowe.wanandroid.ui.navigator.child.series.detail.child

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.lowe.wanandroid.ui.BaseViewModel
import com.lowe.wanandroid.ui.navigator.NavigatorRepository
import com.lowe.wanandroid.utils.tryOffer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class SeriesDetailChildViewModel @Inject constructor(private val repository: NavigatorRepository) :
    BaseViewModel() {

    private val _fetchSeriesDetails = Channel<Int>(Channel.CONFLATED)

    /**
     * 获取对应Tag的体系文章列表
     */
    val seriesDetailListFlow = _fetchSeriesDetails.receiveAsFlow().flatMapLatest {
        repository.getSeriesDetailList(it, DEFAULT_PAGE_SIZE)
    }.cachedIn(viewModelScope)

    fun fetch(id: Int) {
        _fetchSeriesDetails.tryOffer(id)
    }

}