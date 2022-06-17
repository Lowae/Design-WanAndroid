package com.lowe.wanandroid.ui.navigator.child.series.detail.child

import com.lowe.wanandroid.ui.BaseViewModel
import com.lowe.wanandroid.ui.navigator.NavigatorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SeriesDetailChildViewModel @Inject constructor(private val repository: NavigatorRepository) :
    BaseViewModel() {

    fun getSeriesDetailListFlow(id: Int) = repository.getSeriesDetailList(id, DEFAULT_PAGE_SIZE)

}