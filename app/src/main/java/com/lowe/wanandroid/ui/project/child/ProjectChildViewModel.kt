package com.lowe.wanandroid.ui.project.child

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.lowe.wanandroid.ui.BaseViewModel
import com.lowe.wanandroid.ui.project.ProjectRepository
import com.lowe.wanandroid.utils.tryOffer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class ProjectChildViewModel @Inject constructor(private val repository: ProjectRepository) :
    BaseViewModel() {

    private val _fetchProjects = Channel<Int>(Channel.CONFLATED)

    val getProjectListFlow = _fetchProjects.receiveAsFlow().flatMapLatest {
        if (it == ProjectChildFragment.CATEGORY_ID_NEWEST_PROJECT) repository.getNewProjectListFlow(
            DEFAULT_PAGE_SIZE
        ) else repository.getProjectListFlow(DEFAULT_PAGE_SIZE, it)
    }.cachedIn(viewModelScope)

    fun fetch(categoryId: Int) {
        _fetchProjects.tryOffer(categoryId)
    }

}