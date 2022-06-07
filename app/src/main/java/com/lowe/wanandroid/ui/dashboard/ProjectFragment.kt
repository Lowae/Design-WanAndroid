package com.lowe.wanandroid.ui.dashboard

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.FragmentProjectBinding
import com.lowe.wanandroid.ui.BaseFragment

class ProjectFragment :
    BaseFragment<ProjectViewModel, FragmentProjectBinding>(R.layout.fragment_project) {

    private lateinit var childAdapter: ProjectChildFragmentAdapter
    private lateinit var tabLayoutMediator: TabLayoutMediator

    override fun createViewModel() = ProjectViewModel()

    override fun init(savedInstanceState: Bundle?) {
        childAdapter =
            ProjectChildFragmentAdapter(emptyList(), this.parentFragmentManager, lifecycle)
        initView()
        initObserve()
    }

    private fun initObserve() {
        viewModel.projectTitleListLiveData.observe(viewLifecycleOwner) {
            childAdapter.items = it
            childAdapter.notifyDataSetChanged()
        }
    }

    private fun initView() {
        viewBinding.apply {
            with(projectViewPager2) {
                adapter = childAdapter
            }
            tabLayoutMediator = TabLayoutMediator(
                viewBinding.projectTabLayout,
                viewBinding.projectViewPager2
            ) { tab: TabLayout.Tab, position: Int ->
                tab.text = childAdapter.items[position].name
            }.apply(TabLayoutMediator::attach)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        tabLayoutMediator.detach()
    }
}