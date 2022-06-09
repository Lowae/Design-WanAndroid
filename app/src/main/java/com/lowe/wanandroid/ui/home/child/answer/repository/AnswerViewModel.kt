package com.lowe.wanandroid.ui.home.child.answer.repository

import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import com.lowe.wanandroid.services.success
import com.lowe.wanandroid.ui.ArticleDiffCalculator
import com.lowe.wanandroid.ui.BaseViewModel
import com.lowe.wanandroid.ui.home.HomeRepository
import com.lowe.wanandroid.ui.launch

class AnswerViewModel: BaseViewModel() {

    var isLoading = false
    val answerListLiveData = MutableLiveData<Pair<List<Any>, DiffUtil.DiffResult>>()
    private var page = 0

    override fun start() {
        fetchAnswerList(true)
    }

    fun fetchAnswerList(isRefresh: Boolean = false) {
        page = if (isRefresh) 0 else page
        isLoading = true
        launch({
            val answerPageResponse = HomeRepository.getAnswerPageList(page, DEFAULT_PAGE_SIZE)
            val oldList = answerListLiveData.value?.first ?: emptyList()
            val newList = oldList + (answerPageResponse.success()?.data?.datas ?: emptyList())
            answerListLiveData.value = getDiffResultPair(oldList, newList)
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