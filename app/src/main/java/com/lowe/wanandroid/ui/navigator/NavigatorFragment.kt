package com.lowe.wanandroid.ui.navigator

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.lowe.wanandroid.MainViewModel
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.FragmentNotificationsBinding
import com.lowe.wanandroid.ui.BaseFragment

class NavigatorFragment :
    BaseFragment<NavigatorViewModel, FragmentNotificationsBinding>(R.layout.fragment_notifications) {

    companion object {
        const val KEY_NAVIGATOR_CHILD_HOME_TAB_PARCELABLE = "key_navigator_child_tab_parcelable"
    }

    private lateinit var childAdapter: NavigatorChildFragmentAdapter
    private lateinit var tabLayoutMediator: TabLayoutMediator
    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun createViewModel() = NavigatorViewModel()

    override fun init(savedInstanceState: Bundle?) {
        initView()
        initObserve()
    }

    private fun initView() {
        childAdapter =
            NavigatorChildFragmentAdapter(
                generateNavigatorTabs(),
                this.childFragmentManager,
                lifecycle
            )
        viewBinding.apply {
            with(navigatorPager2) {
                adapter = childAdapter
            }
            TabLayoutMediator(
                navigatorTabLayout,
                navigatorPager2
            ) { tab: TabLayout.Tab, position: Int ->
                tab.text = childAdapter.items[position].title
            }.apply(TabLayoutMediator::attach)
            with(swipeRefreshLayout) {
                setOnRefreshListener {
                    this@NavigatorFragment.viewModel.refreshLiveData.value =
                        childAdapter.items[viewBinding.navigatorPager2.currentItem]
                    this.isRefreshing = false
                }
            }
        }
    }

    private fun initObserve() {
        mainViewModel.apply {
            mainTabDoubleClickLiveData.observe(viewLifecycleOwner) {
                viewModel.scrollToTopLiveData.value =
                    childAdapter.items[viewBinding.navigatorPager2.currentItem]
            }
        }
    }

    private fun generateNavigatorTabs() = listOf(
        NavigatorTabBean(NavigatorChildFragmentAdapter.NAVIGATOR_TAB_NAVIGATOR),
        NavigatorTabBean(NavigatorChildFragmentAdapter.NAVIGATOR_TAB_SERIES),
        NavigatorTabBean(NavigatorChildFragmentAdapter.NAVIGATOR_TAB_TUTORIAL)
    )

    override fun onDestroy() {
        super.onDestroy()
        tabLayoutMediator.detach()
    }
}