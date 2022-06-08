package com.lowe.wanandroid.ui.dashboard.child

import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import com.lowe.wanandroid.services.success
import com.lowe.wanandroid.ui.ArticleDiffCalculator
import com.lowe.wanandroid.ui.BaseViewModel
import com.lowe.wanandroid.ui.dashboard.repository.ProjectRepository
import com.lowe.wanandroid.ui.launch

class ProjectChildViewModel : BaseViewModel() {

    companion object {
        private const val DEFAULT_PAGE_SIZE = 20
    }

    private var pageNo = 0

    var isLoading = false
    val projectListLiveData = MutableLiveData<Pair<List<Any>, DiffUtil.DiffResult>>()

    override fun start() {

    }

    fun fetchProjectList(categoryId: Int, isRefresh: Boolean = false) {
        isLoading = true
        pageNo = if (isRefresh) 0 else pageNo
        launch({
            val projects =
                ProjectRepository.getProjectPageList(pageNo, DEFAULT_PAGE_SIZE, categoryId)
            projectListLiveData.value = getDiffResultPair(
                projectListLiveData.value?.first ?: emptyList(),
                projects.success()?.data?.datas ?: emptyList()
            )
            pageNo++
            isLoading = false
        })

    }

    private fun getDiffResultPair(oldList: List<Any>, newList: List<Any>) =
        newList to DiffUtil.calculateDiff(
            ArticleDiffCalculator.getCommonArticleDiffCalculator(
                oldList,
                newList
            )
        )
}