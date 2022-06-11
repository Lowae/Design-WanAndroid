package com.lowe.wanandroid.ui.navigator.child.series.detail.child

import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import com.lowe.wanandroid.services.success
import com.lowe.wanandroid.ui.BaseViewModel
import com.lowe.wanandroid.ui.launch
import com.lowe.wanandroid.ui.navigator.NavigatorDiffCalculator
import com.lowe.wanandroid.ui.navigator.NavigatorRepository

class SeriesDetailChildViewModel : BaseViewModel() {

    val detailListLiveData = MutableLiveData<Pair<List<Any>, DiffUtil.DiffResult>>()

    private var page = 0
    var isLoading = false
    var hasMore = true

    override fun start() {

    }

    fun fetchSeriesDetailList(id: Int, isRefresh: Boolean = false) {
        isLoading = true
        page = if (isRefresh) 0 else page
        launch({
            val list = NavigatorRepository.getSeriesDetailList(page, id, DEFAULT_PAGE_SIZE)
                .success()?.data?.datas ?: emptyList()
            val oldList = detailListLiveData.value?.first ?: emptyList()
            detailListLiveData.value =
                getDiffResultPair(oldList, if (isRefresh) list else oldList + list)
            hasMore = list.isNotEmpty()
            if (hasMore) page++
            isLoading = false
        })
    }

    private fun getDiffResultPair(oldList: List<Any>, newList: List<Any>) =
        newList to DiffUtil.calculateDiff(
            NavigatorDiffCalculator.getNavigatorDiffCalculator(
                oldList,
                newList
            )
        )
}