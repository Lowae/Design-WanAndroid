package com.lowe.wanandroid.ui.message

import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.ActivityMessageBinding
import com.lowe.wanandroid.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MessageActivity :
    BaseActivity<MessageViewModel, ActivityMessageBinding>(R.layout.activity_message) {

    private lateinit var childAdapter: MessageChildFragmentAdapter

    override val viewModel: MessageViewModel by viewModels()

    override fun init(savedInstanceState: Bundle?) {
        initView()
    }

    private fun initView() {
        childAdapter =
            MessageChildFragmentAdapter(generateMessageTabs(), supportFragmentManager, lifecycle)

        viewDataBinding.apply {
            with(messageViewPager2) {
                adapter = childAdapter
            }
            TabLayoutMediator(
                messageTabLayout,
                messageViewPager2
            ) { tab: TabLayout.Tab, position: Int ->
                tab.text = childAdapter.items[position].title
            }.apply(TabLayoutMediator::attach)
            backIcon.setOnClickListener { finish() }
        }
    }

    private fun generateMessageTabs() = listOf(
        MessageTabBean(MessageChildFragmentAdapter.MESSAGE_TAB_NEW),
        MessageTabBean(MessageChildFragmentAdapter.MESSAGE_TAB_HISTORY),
    )

}