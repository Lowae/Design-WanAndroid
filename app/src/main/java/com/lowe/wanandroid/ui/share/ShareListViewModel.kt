package com.lowe.wanandroid.ui.share

import com.lowe.wanandroid.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShareListViewModel @Inject constructor(private val repository: ShareListRepository) :
    BaseViewModel() {

    /**
     * ShareBean数据流
     */
    fun getShareBeanFlow() = repository.shareBeanFlow

    /**
     * 分享列表数据流
     */
    fun getShareFlow() = repository.getShareList()
}