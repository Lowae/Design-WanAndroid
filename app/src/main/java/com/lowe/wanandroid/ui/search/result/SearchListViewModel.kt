package com.lowe.wanandroid.ui.search.result

import com.lowe.wanandroid.ui.BaseViewModel
import com.lowe.wanandroid.ui.search.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchListViewModel @Inject constructor(private val repository: SearchRepository) :
    BaseViewModel() {
}