package com.lowe.wanandroid.ui.home.child.explore

import com.lowe.wanandroid.ui.BaseViewModel
import com.lowe.wanandroid.ui.home.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(private val repository: HomeRepository) :
    BaseViewModel() {

    /**
     * 首页列表数据Flow
     */
    fun getArticlesFlow() = repository.getArticlePageList(20)

}