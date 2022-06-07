package com.lowe.wanandroid.ui.dashboard

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lowe.wanandroid.services.model.ProjectTitle
import com.lowe.wanandroid.ui.dashboard.child.ProjectChildFragment

class ProjectChildFragmentAdapter(
    var items: List<ProjectTitle>,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount() = items.size

    override fun createFragment(position: Int): Fragment {
        return ProjectChildFragment.newInstance(items[position].id)
    }
}