package com.lowe.wanandroid.ui.dashboard

import androidx.lifecycle.MutableLiveData
import com.lowe.wanandroid.services.model.ProjectTitle
import com.lowe.wanandroid.services.success
import com.lowe.wanandroid.ui.BaseViewModel
import com.lowe.wanandroid.ui.dashboard.repository.ProjectRepository
import com.lowe.wanandroid.ui.launch

class ProjectViewModel : BaseViewModel() {

    val projectTitleListLiveData = MutableLiveData<List<ProjectTitle>>()
    val parentRefreshLiveData = MutableLiveData<Int>()

    override fun start() {
        fetchProjectList()
    }

    private fun fetchProjectList() {
        launch({
            projectTitleListLiveData.value =
                ProjectRepository.getProjectTitleList().success()?.data ?: emptyList()
        })
    }
}