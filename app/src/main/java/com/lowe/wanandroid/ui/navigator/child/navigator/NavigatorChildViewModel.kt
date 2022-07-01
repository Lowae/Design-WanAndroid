package com.lowe.wanandroid.ui.navigator.child.navigator

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
class NavigatorChildViewModel @Inject constructor(private val repository: NavigatorRepository) :
    BaseViewModel() {

    val navigationTagListLiveData = MutableLiveData<Pair<List<Any>, DiffUtil.DiffResult>>()

    override fun init() {
        fetchNavigationList()
    }

    private fun fetchNavigationList() {
        launch({
            val navigation = repository.getNavigationList().success()?.data ?: emptyList()
            // 默认第一个选中
            navigationTagListLiveData.value = getDiffResultPair(
                navigationTagListLiveData.value?.first ?: emptyList(),
                navigation
            )
        })
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