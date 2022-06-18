package com.lowe.wanandroid.ui.home.child.square

import com.lowe.wanandroid.ui.BaseViewModel
import com.lowe.wanandroid.ui.home.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SquareViewModel @Inject constructor(private val repository: HomeRepository) :
    BaseViewModel() {

    fun getSquareFlow() = repository.getSquarePageList(DEFAULT_PAGE_SIZE)

}