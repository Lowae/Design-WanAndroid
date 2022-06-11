package com.lowe.wanandroid.ui.navigator.child.navigator

import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import com.lowe.wanandroid.services.success
import com.lowe.wanandroid.ui.BaseViewModel
import com.lowe.wanandroid.ui.launch
import com.lowe.wanandroid.ui.navigator.NavigatorDiffCalculator
import com.lowe.wanandroid.ui.navigator.NavigatorRepository

class NavigatorChildViewModel : BaseViewModel() {

    companion object {
        const val PAYLOAD_TAG_SELECTED_CHANGE = "payload_tag_selected_change"
    }

    val navigationTagListLiveData = MutableLiveData<Pair<List<Any>, DiffUtil.DiffResult>>()

    override fun start() {
        fetchNavigationList()
    }

    fun getCurrentList() = navigationTagListLiveData.value?.first ?: emptyList()

    private fun fetchNavigationList() {
        launch({
            val navigation = NavigatorRepository.getNavigationList().success()?.data ?: emptyList()
            // 默认第一个选中
            val newList = navigation.apply {
                firstOrNull()?.isSelected = true
            }
            navigationTagListLiveData.value = getDiffResultPair(
                navigationTagListLiveData.value?.first ?: emptyList(),
                newList
            )
        })
    }

    private fun getDiffResultPair(
        oldList: List<Any>,
        newList: List<Any>
    ) = newList to DiffUtil.calculateDiff(
        NavigatorDiffCalculator.getNavigatorDiffCalculator(
            oldList,
            newList
        )
    )
}