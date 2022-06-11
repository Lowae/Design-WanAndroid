package com.lowe.wanandroid.ui.navigator.child.series.detail

import android.os.Bundle
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.ActivitySeriesDetailListLayoutBinding
import com.lowe.wanandroid.ui.BaseActivity

class SeriesDetailListActivity : BaseActivity<SeriesDetailListViewModel, ActivitySeriesDetailListLayoutBinding>(R.layout.activity_series_detail_list_layout) {

    override fun createViewModel() = SeriesDetailListViewModel()

    override fun init(savedInstanceState: Bundle?) {

    }

}