package com.lowe.wanandroid.ui.navigator.child.series

import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import com.lowe.wanandroid.services.success
import com.lowe.wanandroid.ui.BaseViewModel
import com.lowe.wanandroid.ui.launch
import com.lowe.wanandroid.ui.navigator.NavigatorDiffCalculator
import com.lowe.wanandroid.ui.navigator.NavigatorRepository

class SeriesChildViewModel : BaseViewModel() {

    val seriesListLiveData = MutableLiveData<Pair<List<Any>, DiffUtil.DiffResult>>()

    override fun start() {

    }

    fun getCurrentList() = seriesListLiveData.value?.first ?: emptyList()

    fun fetchSeriesList() {
        launch({
            val series = NavigatorRepository.getTreeList().success()?.data ?: emptyList()
            val oldList = seriesListLiveData.value?.first ?: emptyList()
            seriesListLiveData.value = getDiffResultPair(oldList, oldList + series)
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