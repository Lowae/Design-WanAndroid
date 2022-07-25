package com.lowe.wanandroid.ui.home.child.answer

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.lowe.common.base.BaseViewModel
import com.lowe.wanandroid.ui.home.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AnswerViewModel @Inject constructor(repository: HomeRepository) :
    BaseViewModel() {

    /**
     * 问答列表数据Flow
     */
    val getAnswerListFlow = repository.getAnswerPageList().cachedIn(viewModelScope)
}