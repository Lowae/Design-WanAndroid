package com.lowe.wanandroid.ui.navigator

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.lowe.wanandroid.BaseFragment
import com.lowe.wanandroid.MainViewModel
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.FragmentNotificationsBinding

/**
 * 导航Fragment
 */
class NavigatorFragment :
    BaseFragment<NavigatorViewModel, FragmentNotificationsBinding>(R.layout.fragment_notifications) {

    companion object {
        const val KEY_NAVIGATOR_CHILD_HOME_TAB_PARCELABLE = "key_navigator_child_tab_parcelable"
    }

    private lateinit var childAdapter: NavigatorChildFragmentAdapter
    private val mainViewModel by activityViewModels<MainViewModel>()

    override val viewModel: NavigatorViewModel by viewModels()

    override fun onViewCreated(savedInstanceState: Bundle?) {
        initView()
        initEvents()
    }

    private fun initView() {
        childAdapter =
            NavigatorChildFragmentAdapter(
                generateNavigatorTabs(),
                this.childFragmentManager,
                lifecycle
            )
        viewDataBinding.apply {
            with(navigatorPager2) {
                adapter = childAdapter
            }
            TabLayoutMediator(
                navigatorTabLayout,
                navigatorPager2
            ) { tab: TabLayout.Tab, position: Int ->
                tab.text = childAdapter.items[position].title
            }.apply(TabLayoutMediator::attach)
        }
    }

    private fun initEvents() {
        mainViewModel.apply {
            mainTabDoubleClickLiveData.observe(viewLifecycleOwner) {
                if (it == this@NavigatorFragment.tag) {
                    viewModel.scrollToTopEvent(childAdapter.items[viewDataBinding.navigatorPager2.currentItem])
                }
            }
        }
    }

    private fun generateNavigatorTabs() = listOf(
        NavigatorTabBean(NavigatorChildFragmentAdapter.NAVIGATOR_TAB_NAVIGATOR),
        NavigatorTabBean(NavigatorChildFragmentAdapter.NAVIGATOR_TAB_SERIES),
        NavigatorTabBean(NavigatorChildFragmentAdapter.NAVIGATOR_TAB_TUTORIAL)
    )
}