package com.lowe.wanandroid.ui.navigator.child.navigator

import androidx.lifecycle.LiveData
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

    private val _navigationTagListLiveData = MutableLiveData<Pair<List<Any>, DiffUtil.DiffResult>>()
    val navigationTagListLiveData: LiveData<Pair<List<Any>, DiffUtil.DiffResult>> =
        _navigationTagListLiveData

    override fun init() {
        fetchNavigationList()
    }

    /**
     * 获取导航数据
     */
    private fun fetchNavigationList() {
        launch({
            val navigation = repository.getNavigationList().success()?.data ?: emptyList()
            _navigationTagListLiveData.value = getDiffResultPair(
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