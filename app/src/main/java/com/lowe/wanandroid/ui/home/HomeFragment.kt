package com.lowe.wanandroid.ui.home

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.FragmentHomeBinding
import com.lowe.wanandroid.ui.BaseFragment

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>(R.layout.fragment_home) {

    private lateinit var childAdapter: HomeChildFragmentAdapter
    private lateinit var tabLayoutMediator: TabLayoutMediator

    override fun createViewModel() = HomeViewModel()

    override fun init(savedInstanceState: Bundle?) {
        initView()
        initObserve()
    }

    override fun onDestroy() {
        super.onDestroy()
        tabLayoutMediator.detach()
    }

    private fun initView() {
        childAdapter =
            HomeChildFragmentAdapter(generateHomeTabs(), this.childFragmentManager, lifecycle)
        viewBinding.apply {
            with(homeViewPager2) {
                adapter = childAdapter
            }
            TabLayoutMediator(
                homeTabLayout,
                homeViewPager2
            ) { tab: TabLayout.Tab, position: Int ->
                tab.text = childAdapter.items[position].title
            }.apply(TabLayoutMediator::attach)
        }
    }

    private fun initObserve() {

    }

    private fun generateHomeTabs() = listOf<HomeTabBean>(
        HomeTabBean("首页"),
        HomeTabBean("广场"),
        HomeTabBean("问答"),
    )
}