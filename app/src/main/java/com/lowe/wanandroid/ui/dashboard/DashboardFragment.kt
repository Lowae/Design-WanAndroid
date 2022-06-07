package com.lowe.wanandroid.ui.dashboard

import android.os.Bundle
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.FragmentDashboardBinding
import com.lowe.wanandroid.ui.BaseFragment

class DashboardFragment : BaseFragment<DashboardViewModel, FragmentDashboardBinding>(R.layout.fragment_dashboard) {
    override fun createViewModel() = DashboardViewModel()

    override fun init(savedInstanceState: Bundle?) {

    }

}