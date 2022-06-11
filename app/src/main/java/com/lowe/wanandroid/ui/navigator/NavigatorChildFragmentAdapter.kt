package com.lowe.wanandroid.ui.navigator

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lowe.wanandroid.ui.navigator.child.navigator.NavigatorChildFragment
import com.lowe.wanandroid.ui.navigator.child.series.SeriesChildFragment
import com.lowe.wanandroid.ui.navigator.child.tutorial.TutorialChildFragment
import kotlinx.parcelize.Parcelize

class NavigatorChildFragmentAdapter(
    var items: List<NavigatorTabBean>,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    companion object {
        const val NAVIGATOR_TAB_NAVIGATOR = "导航"
        const val NAVIGATOR_TAB_SERIES = "体系"
        const val NAVIGATOR_TAB_TUTORIAL = "教程"
    }

    override fun getItemCount() = items.size

    override fun createFragment(position: Int): Fragment {
        return when (items[position].title) {
            NAVIGATOR_TAB_NAVIGATOR -> NavigatorChildFragment.newInstance(items[position])
            NAVIGATOR_TAB_SERIES -> SeriesChildFragment.newInstance(items[position])
            NAVIGATOR_TAB_TUTORIAL -> TutorialChildFragment.newInstance(items[position])
            else -> NavigatorChildFragment.newInstance(items[position])
        }
    }
}

@Parcelize
data class NavigatorTabBean(
    val title: String
) : Parcelable