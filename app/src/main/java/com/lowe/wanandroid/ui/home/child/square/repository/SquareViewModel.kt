package com.lowe.wanandroid.ui.home.child.square.repository

import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import com.lowe.wanandroid.services.success
import com.lowe.wanandroid.ui.ArticleDiffCalculator
import com.lowe.wanandroid.ui.BaseViewModel
import com.lowe.wanandroid.ui.home.HomeRepository
import com.lowe.wanandroid.ui.launch

class SquareViewModel : BaseViewModel() {

    var isLoading = false
    val squareListLiveData = MutableLiveData<Pair<List<Any>, DiffUtil.DiffResult>>()
    private var page = 0

    override fun start() {
        fetchSquareList(true)
    }

    fun fetchSquareList(isRefresh: Boolean = false) {
        page = if (isRefresh) 0 else page
        isLoading = true
        launch({
            val squarePageResponse = HomeRepository.getSquarePageList(page, DEFAULT_PAGE_SIZE)
            val oldList = squareListLiveData.value?.first ?: emptyList()
            val newList = (if (isRefresh) emptyList() else oldList) + (squarePageResponse.success()?.data?.datas ?: emptyList())
            squareListLiveData.value = getDiffResultPair(oldList, newList)
            page++
            isLoading = false
        })
    }

    private fun getDiffResultPair(
        oldList: List<Any>,
        newList: List<Any>
    ): Pair<List<Any>, DiffUtil.DiffResult> = newList to DiffUtil.calculateDiff(
        ArticleDiffCalculator.getCommonArticleDiffCalculator(
            oldList,
            newList
        )
    )
}