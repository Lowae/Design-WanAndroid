package com.lowe.wanandroid.ui.navigator.child.series

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.DiffUtil
import com.lowe.wanandroid.services.model.success
import com.lowe.wanandroid.ui.ArticleDiffCalculator
import com.lowe.wanandroid.ui.BaseViewModel
import com.lowe.wanandroid.ui.navigator.NavigatorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SeriesChildViewModel @Inject constructor(private val repository: NavigatorRepository) :
    BaseViewModel() {

    val seriesListLiveData: LiveData<Pair<List<Any>, DiffUtil.DiffResult>> = liveData {
        val series = repository.getTreeList().success()?.data ?: emptyList()
        val oldList = this.latestValue?.first ?: emptyList()
        emit(getDiffResultPair(oldList, oldList + series))
    }

    fun getCurrentList() = seriesListLiveData.value?.first ?: emptyList()

    private fun getDiffResultPair(oldList: List<Any>, newList: List<Any>) =
        newList to DiffUtil.calculateDiff(
            ArticleDiffCalculator.getCommonDiffCallback(
                oldList,
                newList
            )
        )
}