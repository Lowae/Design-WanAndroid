package com.lowe.wanandroid.base

import android.view.View
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import com.lowe.wanandroid.R

/**
 * 使用Hide/Show处理Fragment，使Fragment执行 onPause/onResume.
 * 避免页面重建.
 * Use Hide/Show to process Fragment and make Fragment execute onPause/onResume.
 * Avoid page reconstruction.
 */
class WithoutReplaceNavHostFragment : NavHostFragment() {

    /**
     * @return 使用自己的FragmentNavigator
     */
    override fun createFragmentNavigator(): Navigator<out FragmentNavigator.Destination> {
        return WithoutReplaceNavigator(requireContext(), childFragmentManager, containerId)
    }


    private val containerId: Int
        private get() {
            val id = id
            return if (id != 0 && id != View.NO_ID) {
                id
                // Fallback to using our own ID if this Fragment wasn't added via
                // add(containerViewId, Fragment)
            } else R.id.nav_host_fragment_activity_main
        }
}