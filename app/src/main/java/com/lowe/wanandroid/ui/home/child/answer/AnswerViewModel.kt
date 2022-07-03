package com.lowe.wanandroid.ui.home.child.answer

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.lowe.wanandroid.ui.BaseViewModel
import com.lowe.wanandroid.ui.home.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AnswerViewModel @Inject constructor(private val repository: HomeRepository) :
    BaseViewModel() {

    /**
     * 问答列表数据Flow
     */
    fun getAnswerListFlow() = repository.getAnswerPageList().cachedIn(viewModelScope)
}