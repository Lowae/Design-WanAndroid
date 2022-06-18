package com.lowe.wanandroid.ui.collect

import com.lowe.wanandroid.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CollectViewModel @Inject constructor(private val repository: CollectRepository) :
    BaseViewModel() {

    fun collectFlow() = repository.getCollectFlow()
}