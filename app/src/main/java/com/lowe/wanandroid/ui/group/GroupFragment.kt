package com.lowe.wanandroid.ui.group

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.lowe.wanandroid.MainViewModel
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.FragmentGroupBinding
import com.lowe.wanandroid.ui.BaseFragment

class GroupFragment :
    BaseFragment<GroupViewModel, FragmentGroupBinding>(R.layout.fragment_group) {

    private lateinit var childAdapter: GroupChildFragmentAdapter
    private lateinit var tabLayoutMediator: TabLayoutMediator
    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun createViewModel() = GroupViewModel()

    override fun init(savedInstanceState: Bundle?) {
        childAdapter =
            GroupChildFragmentAdapter(emptyList(), this.childFragmentManager, lifecycle)
        initView()
        initObserve()
    }

    private fun initObserve() {
        viewModel.authorsNameLiveData.observe(viewLifecycleOwner) {
            childAdapter.items = it
            childAdapter.notifyItemRangeInserted(0, childAdapter.itemCount)
        }
        mainViewModel.mainTabDoubleClickLiveData.observe(viewLifecycleOwner) {
            if (childAdapter.items.isEmpty()) return@observe
            viewModel.scrollToTopLiveData.value =
                childAdapter.items[viewBinding.groupViewPager2.currentItem].id
        }
    }

    private fun initView() {
        viewBinding.apply {
            with(groupViewPager2) {
                adapter = childAdapter
            }
            with(groupSwipeRefresh) {
                setOnRefreshListener {
                    this.isRefreshing = false
                }
            }
            tabLayoutMediator = TabLayoutMediator(
                viewBinding.groupTabLayout,
                viewBinding.groupViewPager2
            ) { tab: TabLayout.Tab, position: Int ->
                tab.text = childAdapter.items[position].name
            }.apply(TabLayoutMediator::attach)
        }
    }
}