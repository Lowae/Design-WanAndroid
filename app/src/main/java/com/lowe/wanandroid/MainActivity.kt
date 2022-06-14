package com.lowe.wanandroid

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import com.lowe.wanandroid.databinding.ActivityMainBinding
import com.lowe.wanandroid.ui.BaseActivity
import com.lowe.wanandroid.ui.BaseFragment
import com.lowe.wanandroid.ui.group.GroupFragment
import com.lowe.wanandroid.ui.home.HomeFragment
import com.lowe.wanandroid.ui.home.child.explore.ExploreFragment
import com.lowe.wanandroid.ui.navigator.NavigatorFragment
import com.lowe.wanandroid.ui.profile.ProfileFragment
import com.lowe.wanandroid.ui.project.ProjectFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(R.layout.activity_main) {

    companion object {
        private const val KEY_CURRENT_FRAGMENT_INDEX = "key_current_fragment_index"
    }

    private lateinit var binding: ActivityMainBinding

    private var fragmentList =
        listOf(
            HomeFragment(),
            ProjectFragment(),
            NavigatorFragment(),
            GroupFragment(),
            ProfileFragment()
        )
    private var activeFragmentIndex = -1

    override val viewModel: MainViewModel by viewModels()

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

    private fun onBottomItemSelect(item: MenuItem): Boolean {
        switchFragment(getFragmentIndexFromItemId(item.itemId))
        return true
    }

    private fun onBottomDoubleClick(item: MenuItem) {
        viewModel.mainTabDoubleClickLiveData.value = getTagFromItemId(item.itemId)
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

    private fun getTagFromItemId(itemId: Int) = fragmentList[getFragmentIndexFromItemId(itemId)].tag
        ?: ExploreFragment::class.java.simpleName

    private fun getFragmentIndexFromItemId(itemId: Int): Int {
        return when (itemId) {
            R.id.navigation_home -> 0
            R.id.navigation_project -> 1
            R.id.navigation_navigator -> 2
            R.id.navigation_we_chat_group -> 3
            R.id.navigation_profile -> 4
            else -> 0
        }
    }
}