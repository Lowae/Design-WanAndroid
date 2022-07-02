package com.lowe.wanandroid.ui.group

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lowe.wanandroid.services.model.Classify
import com.lowe.wanandroid.ui.group.child.GroupChildFragment

class GroupChildFragmentAdapter(
    var items: List<Classify>,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount() = items.size

    override fun createFragment(position: Int) = GroupChildFragment.newInstance(items[position].id)
}