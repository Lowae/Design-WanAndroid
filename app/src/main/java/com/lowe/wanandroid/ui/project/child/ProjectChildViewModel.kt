package com.lowe.wanandroid.ui.project.child

import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import com.lowe.wanandroid.services.success
import com.lowe.wanandroid.ui.ArticleDiffCalculator
import com.lowe.wanandroid.ui.BaseViewModel
import com.lowe.wanandroid.ui.launch
import com.lowe.wanandroid.ui.project.repository.ProjectRepository

class ProjectChildViewModel : BaseViewModel() {

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
                (if (categoryId == ProjectChildFragment.CATEGORY_ID_NEWEST_PROJECT)
                    ProjectRepository.getNewProjectPageList(pageNo, DEFAULT_PAGE_SIZE)
                else ProjectRepository.getProjectPageList(
                    pageNo,
                    DEFAULT_PAGE_SIZE,
                    categoryId
                )).success()?.data?.datas ?: emptyList()

            val oldList = projectListLiveData.value?.first ?: emptyList()
            val newList = if (isRefresh) projects else oldList + projects
            projectListLiveData.value = getDiffResultPair(oldList, newList)
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