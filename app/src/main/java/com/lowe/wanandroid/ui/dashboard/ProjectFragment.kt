package com.lowe.wanandroid.ui.dashboard

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.lowe.wanandroid.MainViewModel
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.FragmentProjectBinding
import com.lowe.wanandroid.ui.BaseFragment

class ProjectFragment :
    BaseFragment<ProjectViewModel, FragmentProjectBinding>(R.layout.fragment_project) {

    private lateinit var childAdapter: ProjectChildFragmentAdapter
    private lateinit var tabLayoutMediator: TabLayoutMediator
    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun createViewModel() = ProjectViewModel()

    override fun init(savedInstanceState: Bundle?) {
        childAdapter =
            ProjectChildFragmentAdapter(emptyList(), this.childFragmentManager, lifecycle)
        initView()
        initObserve()
    }

    private fun initObserve() {
        viewModel.projectTitleListLiveData.observe(viewLifecycleOwner) {
            childAdapter.items = it
            childAdapter.notifyDataSetChanged()
        }
        mainViewModel.mainTabDoubleClickLiveData.observe(viewLifecycleOwner) {
            if (childAdapter.items.isEmpty()) return@observe
            viewModel.scrollToTopLiveData.value =
                childAdapter.items[viewBinding.projectViewPager2.currentItem].id
        }
    }

    private fun initView() {
        viewBinding.apply {
            with(projectViewPager2) {
                adapter = childAdapter
            }
            with(projectSwipeRefresh) {
                setOnRefreshListener {
                    this@ProjectFragment.viewModel.parentRefreshLiveData.value =
                        childAdapter.items[projectViewPager2.currentItem].id
                    this.isRefreshing = false
                }
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