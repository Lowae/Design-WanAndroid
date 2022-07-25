package com.lowe.wanandroid.ui.navigator.child.series.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lowe.common.base.BaseViewModel
import com.lowe.common.services.model.Classify

class SeriesDetailListViewModel : BaseViewModel() {

    private val _onRefreshLiveData = MutableLiveData<Classify>()
    val onRefreshLiveData: LiveData<Classify> = _onRefreshLiveData

    fun onRefreshEvent(classify: Classify) {
        _onRefreshLiveData.value = classify
    }
}