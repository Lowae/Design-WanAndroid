package com.lowe.wanandroid.ui.project

import androidx.lifecycle.MutableLiveData
import com.lowe.wanandroid.services.model.ProjectTitle
import com.lowe.wanandroid.services.model.success
import com.lowe.wanandroid.ui.BaseViewModel
import com.lowe.wanandroid.ui.launch
import com.lowe.wanandroid.ui.project.child.ProjectChildFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProjectViewModel @Inject constructor(private val repository: ProjectRepository): BaseViewModel() {

    val projectTitleListLiveData = MutableLiveData<List<ProjectTitle>>()
    val parentRefreshLiveData = MutableLiveData<Int>()
    val scrollToTopLiveData = MutableLiveData<Int>()

    override fun init() {
        fetchProjectList()
    }

    private fun fetchProjectList() {
        launch({
            projectTitleListLiveData.value =
                mutableListOf<ProjectTitle>().apply {
                    add(generateNewestProjectBean())
                    addAll(repository.getProjectTitleList().success()?.data ?: emptyList())
                }
        })
    }

    private fun generateNewestProjectBean() = ProjectTitle(
        id = ProjectChildFragment.CATEGORY_ID_NEWEST_PROJECT, name = "最新项目"
    )
}