package com.lowe.wanandroid.ui.navigator.child.navigator

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.DiffUtil
import com.lowe.common.base.BaseViewModel
import com.lowe.common.base.http.adapter.getOrElse
import com.lowe.wanandroid.ui.ArticleDiffCalculator
import com.lowe.wanandroid.ui.navigator.NavigatorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NavigatorChildViewModel @Inject constructor(private val repository: NavigatorRepository) :
    BaseViewModel() {

    /**
     * 获取导航数据
     */
    val navigationTagListLiveData: LiveData<Pair<List<Any>, DiffUtil.DiffResult>> = liveData {
        emit(
            getDiffResultPair(
                this.latestValue?.first ?: emptyList(),
                repository.getNavigationList().getOrElse { emptyList() }
            )
        )
    }

    private fun getDiffResultPair(
        oldList: List<Any>,
        newList: List<Any>
    ) = newList to DiffUtil.calculateDiff(
        ArticleDiffCalculator.getCommonDiffCallback(
            oldList,
            newList
        )
    )
}