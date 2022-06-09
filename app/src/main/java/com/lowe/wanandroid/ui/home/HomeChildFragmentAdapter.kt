package com.lowe.wanandroid.ui.home

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lowe.wanandroid.ui.home.child.answer.AnswerFragment
import com.lowe.wanandroid.ui.home.child.explore.ExploreFragment
import com.lowe.wanandroid.ui.home.child.square.SquareFragment
import kotlinx.parcelize.Parcelize

class HomeChildFragmentAdapter(
    var items: List<HomeTabBean>,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    companion object {
        const val HOME_TAB_EXPLORE = "首页"
        const val HOME_TAB_SQUARE = "广场"
        const val HOME_TAB_ANSWER = "问答"
    }

    override fun getItemCount() = items.size

    override fun createFragment(position: Int): Fragment {
        return when (items[position].title) {
            HOME_TAB_EXPLORE -> ExploreFragment.newInstance(items[position])
            HOME_TAB_SQUARE -> SquareFragment.newInstance(items[position])
            HOME_TAB_ANSWER -> AnswerFragment.newInstance(items[position])
            else -> ExploreFragment.newInstance(items[position])
        }
    }
}

@Parcelize
data class HomeTabBean(
    val title: String
) : Parcelable