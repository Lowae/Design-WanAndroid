package com.lowe.wanandroid.ui.navigator.child.series.detail

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lowe.wanandroid.services.model.Classify
import com.lowe.wanandroid.ui.navigator.child.series.detail.child.SeriesDetailChildFragment

class SeriesDetailFragmentStateAdapter(
    var items: List<Classify>,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount() = items.size

    override fun createFragment(position: Int) =
        SeriesDetailChildFragment.newInstance(items[position])
}