package com.lowe.wanandroid.ui.collect

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.lowe.common.base.BaseViewModel
import com.lowe.common.services.repository.CollectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CollectViewModel @Inject constructor(repository: CollectRepository) :
    BaseViewModel() {

    /**
     * 收藏文章列表Flow
     */
    val collectFlow = repository.getCollectFlow().cachedIn(viewModelScope)
}