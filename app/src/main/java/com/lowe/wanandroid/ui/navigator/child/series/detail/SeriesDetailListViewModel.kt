package com.lowe.wanandroid.ui.navigator.child.series.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lowe.wanandroid.services.model.Classify
import com.lowe.wanandroid.ui.BaseViewModel

class SeriesDetailListViewModel : BaseViewModel() {

    private val _onRefreshLiveData = MutableLiveData<Classify>()
    val onRefreshLiveData: LiveData<Classify> = _onRefreshLiveData

    fun onRefreshEvent(classify: Classify) {
        _onRefreshLiveData.value = classify
    }

    override fun init() {

    }
}