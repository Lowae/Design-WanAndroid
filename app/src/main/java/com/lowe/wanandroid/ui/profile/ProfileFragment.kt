package com.lowe.wanandroid.ui.profile

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import com.google.android.material.appbar.AppBarLayout
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.FragmentProfileBinding
import com.lowe.wanandroid.ui.BaseFragment
import com.lowe.wanandroid.ui.profile.item.ProfileItemBinder
import com.lowe.wanandroid.utils.ToastEx.showShortToast
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import okhttp3.internal.immutableListOf
import kotlin.math.abs

class ProfileFragment :
    BaseFragment<ProfileViewModel, FragmentProfileBinding>(R.layout.fragment_profile) {


    private val profileItemAdapter = MultiTypeAdapter()
    private var collapsingToolBarStateFlow =
        MutableStateFlow(ProfileCollapsingToolBarState.EXPANDED)

    override fun createViewModel() = ProfileViewModel()

    override fun init(savedInstanceState: Bundle?) {
        initView()
        initItems()
        initObserve()
    }

    private fun initView() {
        profileItemAdapter.register(ProfileItemBinder(this::onOptionClick))
        viewBinding.apply {
            with(this.itemContainer.profileItemList) {
                adapter = profileItemAdapter
                layoutManager = LinearLayoutManager(context)
            }
            with(appBarLayout) {
                addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                    when {
                        verticalOffset == 0 -> collapsingToolBarStateFlow.value =
                            ProfileCollapsingToolBarState.EXPANDED
                        abs(verticalOffset) >= appBarLayout.totalScrollRange -> collapsingToolBarStateFlow.value =
                            ProfileCollapsingToolBarState.COLLAPSED
                        else -> collapsingToolBarStateFlow.value =
                            ProfileCollapsingToolBarState.INTERMEDIATE
                    }
                })
            }
        }
    }


    private fun initItems() {
        profileItemAdapter.items = immutableListOf(
            ProfileItemBean(
                R.drawable.ic_notification_48dp,
                getString(R.string.profile_item_title_message)
            ),
            ProfileItemBean(R.drawable.ic_share_48dp, getString(R.string.profile_item_title_share)),
            ProfileItemBean(R.drawable.ic_collect, getString(R.string.profile_item_title_favorite)),
            ProfileItemBean(R.drawable.ic_tool_48dp, getString(R.string.profile_item_title_tools)),
            ProfileItemBean(
                R.drawable.ic_code_48dp,
                getString(R.string.profile_item_title_project_page)
            ),
        )
        profileItemAdapter.notifyItemRangeChanged(0, profileItemAdapter.itemCount)
    }


    private fun initObserve() {
        lifecycleScope.launchWhenCreated {
            collapsingToolBarStateFlow
                .distinctUntilChanged { old, new ->
                    old == new
                }.collectLatest {
                    if (it == ProfileCollapsingToolBarState.COLLAPSED)
                        viewBinding.collapsingToolbarLayout.title = "Lowae"
                    else viewBinding.collapsingToolbarLayout.title = ""
                }
        }
    }

    private fun onOptionClick(position: Int, item: ProfileItemBean) {
        item.title.showShortToast()
        when (item.title) {
            getString(R.string.profile_item_title_message) -> {
            }
            getString(R.string.profile_item_title_share) -> {

            }
            getString(R.string.profile_item_title_favorite) -> {

            }
            getString(R.string.profile_item_title_tools) -> {

            }
            getString(R.string.profile_item_title_project_page) -> {

            }
        }
    }
}
