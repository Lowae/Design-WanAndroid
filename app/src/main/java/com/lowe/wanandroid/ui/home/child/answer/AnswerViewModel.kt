package com.lowe.wanandroid.ui.home.child.answer

import com.lowe.wanandroid.ui.BaseViewModel
import com.lowe.wanandroid.ui.home.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AnswerViewModel @Inject constructor(private val repository: HomeRepository) :
    BaseViewModel() {

    fun getAnswerListFlow() = repository.getAnswerPageList()
}