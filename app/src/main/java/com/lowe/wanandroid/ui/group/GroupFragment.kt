package com.lowe.wanandroid.ui.group

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.lowe.wanandroid.MainViewModel
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.FragmentGroupBinding
import com.lowe.wanandroid.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupFragment :
    BaseFragment<GroupViewModel, FragmentGroupBinding>(R.layout.fragment_group) {

    private lateinit var childAdapter: GroupChildFragmentAdapter
    private lateinit var tabLayoutMediator: TabLayoutMediator
    private val mainViewModel by activityViewModels<MainViewModel>()

    override val viewModel: GroupViewModel by viewModels()

    override fun onViewCreated(savedInstanceState: Bundle?) {
        childAdapter =
            GroupChildFragmentAdapter(emptyList(), this.childFragmentManager, lifecycle)
        initView()
        initEvents()
    }

    private fun initEvents() {
        viewModel.authorsNameLiveData.observe(viewLifecycleOwner) {
            childAdapter.items = it
            childAdapter.notifyItemRangeInserted(0, childAdapter.itemCount)
        }
        mainViewModel.mainTabDoubleClickLiveData.observe(viewLifecycleOwner) {
            if (childAdapter.items.isEmpty()) return@observe
            viewModel.scrollToTopLiveData.value =
                childAdapter.items[viewDataBinding.groupViewPager2.currentItem].id
        }
    }

    private fun initView() {
        viewDataBinding.apply {
            with(groupViewPager2) {
                adapter = childAdapter
            }
            with(groupSwipeRefresh) {
                setOnRefreshListener {
                    this@GroupFragment.viewModel.parentRefreshLiveData.value =
                        childAdapter.items[groupViewPager2.currentItem].id
                    this.isRefreshing = false
                }
            }
            tabLayoutMediator = TabLayoutMediator(
                viewDataBinding.groupTabLayout,
                viewDataBinding.groupViewPager2
            ) { tab: TabLayout.Tab, position: Int ->
                tab.text = childAdapter.items[position].name
            }.apply(TabLayoutMediator::attach)
        }
    }
}