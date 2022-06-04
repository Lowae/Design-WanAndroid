package com.lowe.wanandroid.base

import android.content.Context
import android.util.Log
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator

/**
 * 使用Hide/Show处理Fragment，使Fragment执行 onPause/onResume.
 * 避免页面重建.
 * Use Hide/Show to process Fragment and make Fragment execute onPause/onResume.
 * Avoid page reconstruction.
 */
@Navigator.Name("fragment")
class WithoutReplaceNavigator(
    private val mContext: Context,
    private val mFragmentManager: FragmentManager,
    private val mContainerId: Int
) : FragmentNavigator(mContext, mFragmentManager, mContainerId) {

    private var savedIds: MutableSet<String>? = null

    init {
        try {
            val field = FragmentNavigator::class.java.getDeclaredField("savedIds")
            field.isAccessible = true
            savedIds = field[this] as MutableSet<String>
        } catch (e: Exception) {
            Log.d(TAG, "反射获取SavedIds失败: ${e.toString()}")
        }
    }

    override fun navigate(
        entries: List<NavBackStackEntry>,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ) {
        if (mFragmentManager.isStateSaved) {
            Log.i(
                TAG, "Ignoring navigate() call: FragmentManager has already saved its state"
            )
            return
        }
        for (entry in entries) {
            navigate(entry, navOptions, navigatorExtras)
        }
    }

    private fun navigate(
        entry: NavBackStackEntry,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ) {
        val backStack = state.backStack.value
        val initialNavigation = backStack.isEmpty()
        val restoreState = (
                navOptions != null && !initialNavigation &&
                        navOptions.shouldRestoreState() &&
                        savedIds?.remove(entry.id) == true
                )
        if (restoreState) {
            // Restore back stack does all the work to restore the entry
            mFragmentManager.restoreBackStack(entry.id)
            state.push(entry)
            return
        }
        val destination = entry.destination as Destination
        val args = entry.arguments
        var className = destination.className
        if (className[0] == '.') {
            className = mContext.packageName + className
        }
        //Modify 修改
        //val frag = mFragmentManager.fragmentFactory.instantiate(mContext.classLoader, className)
        val ft = mFragmentManager.beginTransaction()
        var enterAnim = navOptions?.enterAnim ?: -1
        var exitAnim = navOptions?.exitAnim ?: -1
        var popEnterAnim = navOptions?.popEnterAnim ?: -1
        var popExitAnim = navOptions?.popExitAnim ?: -1
        if (enterAnim != -1 || exitAnim != -1 || popEnterAnim != -1 || popExitAnim != -1) {
            enterAnim = if (enterAnim != -1) enterAnim else 0
            exitAnim = if (exitAnim != -1) exitAnim else 0
            popEnterAnim = if (popEnterAnim != -1) popEnterAnim else 0
            popExitAnim = if (popExitAnim != -1) popExitAnim else 0
            ft.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim)
        }

        //region 添加的代码
        var frag: Fragment? = mFragmentManager.primaryNavigationFragment
        if (frag != null) {
            ft.setMaxLifecycle(frag, Lifecycle.State.STARTED)
            ft.hide(frag)
        }

        val tag = destination.id.toString()
        frag = mFragmentManager.findFragmentByTag(tag)
        if (frag != null) {
            ft.setMaxLifecycle(frag, Lifecycle.State.RESUMED)
            ft.show(frag)
        } else {
            frag = mFragmentManager.fragmentFactory.instantiate(mContext.classLoader, className)
            frag.arguments = args
            ft.add(mContainerId, frag, tag)
        }
        //endregion

        //Modify 修改
        //ft.replace(mContainerId, frag)
        ft.setPrimaryNavigationFragment(frag)
        @IdRes val destId = destination.id
        // TODO Build first class singleTop behavior for fragments
        val isSingleTopReplacement = (
                navOptions != null && !initialNavigation &&
                        navOptions.shouldLaunchSingleTop() &&
                        backStack.last().destination.id == destId
                )
        val isAdded = when {
            initialNavigation -> {
                true
            }
            isSingleTopReplacement -> {
                // Single Top means we only want one instance on the back stack
                if (backStack.size > 1) {
                    // If the Fragment to be replaced is on the FragmentManager's
                    // back stack, a simple replace() isn't enough so we
                    // remove it from the back stack and put our replacement
                    // on the back stack in its place
                    mFragmentManager.popBackStack(
                        entry.id,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                    ft.addToBackStack(entry.id)
                }
                false
            }
            else -> {
                ft.addToBackStack(entry.id)
                true
            }
        }
        if (navigatorExtras is Extras) {
            for ((key, value) in navigatorExtras.sharedElements) {
                ft.addSharedElement(key, value)
            }
        }
        ft.setReorderingAllowed(true)
        ft.commit()
        // The commit succeeded, update our view of the world
        if (isAdded) {
            state.push(entry)
        }
    }

    companion object {
        private const val TAG = "HSFragmentNavigator"
    }
}