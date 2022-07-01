package com.lowe.wanandroid.ui.navigator.child.series

import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import com.lowe.wanandroid.services.model.success
import com.lowe.wanandroid.ui.ArticleDiffCalculator
import com.lowe.wanandroid.ui.BaseViewModel
import com.lowe.wanandroid.ui.launch
import com.lowe.wanandroid.ui.navigator.NavigatorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SeriesChildViewModel @Inject constructor(private val repository: NavigatorRepository) :
    BaseViewModel() {

    val seriesListLiveData = MutableLiveData<Pair<List<Any>, DiffUtil.DiffResult>>()

    fun getCurrentList() = seriesListLiveData.value?.first ?: emptyList()

    fun fetchSeriesList() {
        launch({
            val series = repository.getTreeList().success()?.data ?: emptyList()
            val oldList = seriesListLiveData.value?.first ?: emptyList()
            seriesListLiveData.value = getDiffResultPair(oldList, oldList + series)
        })
    }

    private fun getDiffResultPair(oldList: List<Any>, newList: List<Any>) =
        newList to DiffUtil.calculateDiff(
            ArticleDiffCalculator.getCommonDiffCallback(
                oldList,
                newList
            )
        )
}