package com.lowe.wanandroid.ui.notifications

import android.os.Bundle
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.FragmentNotificationsBinding
import com.lowe.wanandroid.ui.BaseFragment

class NotificationsFragment : BaseFragment<NotificationsViewModel, FragmentNotificationsBinding>(R.layout.fragment_notifications) {
    override fun createViewModel() = NotificationsViewModel()

    override fun init(savedInstanceState: Bundle?) {

    }
}