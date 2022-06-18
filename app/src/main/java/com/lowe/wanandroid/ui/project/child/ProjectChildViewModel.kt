package com.lowe.wanandroid.ui.project.child

import com.lowe.wanandroid.ui.BaseViewModel
import com.lowe.wanandroid.ui.project.ProjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProjectChildViewModel @Inject constructor(private val repository: ProjectRepository) :
    BaseViewModel() {

    fun getProjectListFlow(categoryId: Int) =
        if (categoryId == ProjectChildFragment.CATEGORY_ID_NEWEST_PROJECT) repository.getNewProjectListFlow(
            DEFAULT_PAGE_SIZE
        ) else repository.getProjectListFlow(DEFAULT_PAGE_SIZE, categoryId)
}