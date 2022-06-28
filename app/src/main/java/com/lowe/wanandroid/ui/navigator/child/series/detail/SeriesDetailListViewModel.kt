package com.lowe.wanandroid.ui.navigator.child.series.detail

import androidx.lifecycle.MutableLiveData
import com.lowe.wanandroid.services.model.Classify
import com.lowe.wanandroid.ui.BaseViewModel

class SeriesDetailListViewModel : BaseViewModel() {

    val onRefreshLiveData = MutableLiveData<Classify>()

    override fun init() {

    }
}