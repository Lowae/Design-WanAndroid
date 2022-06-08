package com.lowe.wanandroid.ui.dashboard

import androidx.lifecycle.MutableLiveData
import com.lowe.wanandroid.services.model.ProjectTitle
import com.lowe.wanandroid.services.success
import com.lowe.wanandroid.ui.BaseViewModel
import com.lowe.wanandroid.ui.dashboard.child.ProjectChildFragment
import com.lowe.wanandroid.ui.dashboard.repository.ProjectRepository
import com.lowe.wanandroid.ui.launch

class ProjectViewModel : BaseViewModel() {

    val projectTitleListLiveData = MutableLiveData<List<ProjectTitle>>()
    val parentRefreshLiveData = MutableLiveData<Int>()
    val scrollToTopLiveData = MutableLiveData<Int>()

    override fun start() {
        fetchProjectList()
    }

    private fun fetchProjectList() {
        launch({
            projectTitleListLiveData.value =
                mutableListOf<ProjectTitle>().apply {
                    add(generateNewestProjectBean())
                    addAll(ProjectRepository.getProjectTitleList().success()?.data ?: emptyList())
                }
        })
    }

    private fun generateNewestProjectBean() = ProjectTitle(
        id = ProjectChildFragment.CATEGORY_ID_NEWEST_PROJECT, name = "最新项目"
    )
}