package com.lowe.wanandroid.ui.navigator.child.navigator

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import com.lowe.wanandroid.base.SimpleDiffCalculator
import com.lowe.wanandroid.services.model.Article
import com.lowe.wanandroid.services.model.Navigation
import com.lowe.wanandroid.services.success
import com.lowe.wanandroid.ui.BaseViewModel
import com.lowe.wanandroid.ui.launch
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


    fun onTagSelectedChange(position: Int) {

    }

    private fun getDiffResultPair(
        oldList: List<Any>,
        newList: List<Any>
    ) = newList to DiffUtil.calculateDiff(
        SimpleDiffCalculator(
            oldList,
            newList,
            { oldItem: Any, newItem: Any ->
                when {
                    oldItem is Navigation && newItem is Navigation -> oldItem.name == newItem.name
                    oldItem is Article && newItem is Article -> oldItem.id == newItem.id
                    else -> oldItem.javaClass == newItem.javaClass
                }
            },
            { oldItem: Any, newItem: Any ->
                when {
                    oldItem is Navigation && newItem is Navigation -> {
                        Log.d(
                            "NavigatorChildViewModel",
                            "old: ${oldItem.isSelected} - new: ${newItem.isSelected}"
                        )
                        oldItem == newItem && oldItem.isSelected == newItem.isSelected
                    }
                    oldItem is Article && newItem is Article -> oldItem == newItem
                    else -> oldItem.javaClass == newItem.javaClass
                }
            },
            { oldItem: Any, newItem: Any ->
                when {
                    oldItem is Navigation && newItem is Navigation -> {
                        if (oldItem.isSelected != newItem.isSelected) listOf<NavigatorChildPayload>(
                            TagSelectedChange
                        ) else null
                    }
                    else -> null
                }
            }
        )
    )
}