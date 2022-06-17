package com.lowe.wanandroid.ui.group.child

import com.lowe.wanandroid.ui.BaseViewModel
import com.lowe.wanandroid.ui.group.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GroupChildViewModel @Inject constructor(private val repository: GroupRepository) : BaseViewModel() {

    fun getGroupArticlesFlow(id: Int) = repository.getAuthorArticles(id)

}