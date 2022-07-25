package com.lowe.wanandroid.ui.project

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.lowe.common.utils.fromHtml
import com.lowe.wanandroid.BaseFragment
import com.lowe.wanandroid.MainViewModel
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.FragmentProjectBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * 项目Tab
 */
@AndroidEntryPoint
class ProjectFragment :
    BaseFragment<ProjectViewModel, FragmentProjectBinding>(R.layout.fragment_project) {

    private lateinit var childAdapter: ProjectChildFragmentAdapter
    private lateinit var tabLayoutMediator: TabLayoutMediator
    private val mainViewModel by activityViewModels<MainViewModel>()

    override val viewModel: ProjectViewModel by viewModels()

    override fun onViewCreated(savedInstanceState: Bundle?) {
        childAdapter =
            ProjectChildFragmentAdapter(emptyList(), this.childFragmentManager, lifecycle)
        initView()
        initEvents()
    }

    private fun initEvents() {
        viewModel.projectTitleListLiveData.observe(viewLifecycleOwner) {
            childAdapter.items = it
            childAdapter.notifyDataSetChanged()
        }
        mainViewModel.mainTabDoubleClickLiveData.observe(viewLifecycleOwner) {
            if (childAdapter.items.isEmpty() || it != this.tag) return@observe
            viewModel.scrollToTopLiveData.value =
                childAdapter.items[viewDataBinding.projectViewPager2.currentItem].id
        }
    }

    private fun initView() {
        viewDataBinding.apply {
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
                viewDataBinding.projectTabLayout,
                viewDataBinding.projectViewPager2
            ) { tab: TabLayout.Tab, position: Int ->
                tab.text = childAdapter.items[position].name.fromHtml()
            }.apply(TabLayoutMediator::attach)
        }
    }
}