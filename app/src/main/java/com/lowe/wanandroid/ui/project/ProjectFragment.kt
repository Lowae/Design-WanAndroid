package com.lowe.wanandroid.ui.project

import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.lowe.wanandroid.MainViewModel
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.FragmentProjectBinding
import com.lowe.wanandroid.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProjectFragment :
    BaseFragment<ProjectViewModel, FragmentProjectBinding>(R.layout.fragment_project) {

    private lateinit var childAdapter: ProjectChildFragmentAdapter
    private lateinit var tabLayoutMediator: TabLayoutMediator
    private val mainViewModel by activityViewModels<MainViewModel>()

    override val viewModel: ProjectViewModel by viewModels()

    override fun init(savedInstanceState: Bundle?) {
        childAdapter =
            ProjectChildFragmentAdapter(emptyList(), this.childFragmentManager, lifecycle)
        initView()
        initObserve()
    }

    private fun initObserve() {
        viewModel.projectTitleListLiveData.observe(viewLifecycleOwner) {
            childAdapter.items = it
            childAdapter.notifyItemRangeInserted(0, childAdapter.itemCount)
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
                tab.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Html.fromHtml(childAdapter.items[position].name, Html.FROM_HTML_MODE_LEGACY);
                } else {
                    Html.fromHtml(childAdapter.items[position].name);
                }
            }.apply(TabLayoutMediator::attach)
        }
    }
}