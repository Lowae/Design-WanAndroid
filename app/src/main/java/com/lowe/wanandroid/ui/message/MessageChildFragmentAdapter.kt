package com.lowe.wanandroid.ui.message

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lowe.wanandroid.ui.message.child.MessageTabChildFragment
import kotlinx.parcelize.Parcelize

class MessageChildFragmentAdapter(
    var items: List<MessageTabBean>,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    companion object {
        const val MESSAGE_TAB_NEW = "新消息"
        const val MESSAGE_TAB_HISTORY = "历史消息"

        const val KEY_MESSAGE_CHILD_TAB_PARCELABLE = "key_message_child_tab_parcelable"
    }

    override fun getItemCount() = items.size

    override fun createFragment(position: Int): Fragment {
        return MessageTabChildFragment.newInstance(items[position])
    }
}

@Parcelize
data class MessageTabBean(
    val title: String
) : Parcelable