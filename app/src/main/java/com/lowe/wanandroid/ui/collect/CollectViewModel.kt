package com.lowe.wanandroid.ui.collect

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.lowe.wanandroid.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CollectViewModel @Inject constructor(private val repository: CollectRepository) :
    BaseViewModel() {

    /**
     * 收藏文章列表Flow
     */
    val collectFlow = repository.getCollectFlow().cachedIn(viewModelScope)
}