package com.lowe.wanandroid

import android.os.Bundle
import android.view.MenuItem
import com.lowe.wanandroid.databinding.ActivityMainBinding
import com.lowe.wanandroid.ui.BaseActivity
import com.lowe.wanandroid.ui.BaseFragment
import com.lowe.wanandroid.ui.dashboard.DashboardFragment
import com.lowe.wanandroid.ui.home.HomeFragment
import com.lowe.wanandroid.ui.notifications.NotificationsFragment

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(R.layout.activity_main) {

    companion object {
        private const val KEY_CURRENT_FRAGMENT_INDEX = "key_current_fragment_index"
    }

    private lateinit var binding: ActivityMainBinding

    private var fragmentList = listOf(HomeFragment(), DashboardFragment(), NotificationsFragment())
    private var activeFragmentIndex = -1

    override fun createViewModel() = MainViewModel()

    override fun init(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.navView.setOnItemSelectedListener(
            NavBottomViewDoubleClickListener(
                this::onBottomItemSelect,
                this::onBottomDoubleClick
            )
        )
        if (savedInstanceState == null) {
            switchFragment(0)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(KEY_CURRENT_FRAGMENT_INDEX, activeFragmentIndex)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        fragmentList = fragmentList.map {
            supportFragmentManager.findFragmentByTag(it.javaClass.simpleName) as? BaseFragment<*, *>
                ?: it
        }
        switchFragment(savedInstanceState.getInt(KEY_CURRENT_FRAGMENT_INDEX, 0))
    }

    private fun onBottomItemSelect(item: MenuItem) =
        when (item.itemId) {
            R.id.navigation_home -> {
                switchFragment(0)
                true
            }
            R.id.navigation_dashboard -> {
                switchFragment(1)
                true
            }
            R.id.navigation_notifications -> {
                switchFragment(2)
                true
            }
            else -> false
        }

    private fun onBottomDoubleClick(item: MenuItem) =
        when (item.itemId) {
            R.id.navigation_home -> {

            }
            else -> Unit
        }

    private fun switchFragment(fragmentIndex: Int) {
        if (fragmentIndex != activeFragmentIndex) {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            val fragment = fragmentList[fragmentIndex]
            fragmentList.getOrNull(activeFragmentIndex)?.apply(fragmentTransaction::hide)
            if (!fragment.isAdded) {
                fragmentTransaction
                    .add(
                        R.id.nav_host_fragment_activity_main,
                        fragment,
                        fragment.javaClass.simpleName
                    )
                    .show(fragment)
            } else {
                fragmentTransaction.show(fragment)
            }
            fragmentTransaction.commitAllowingStateLoss()
            activeFragmentIndex = fragmentIndex
        }
    }
}