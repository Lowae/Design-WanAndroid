package com.lowe.wanandroid.ui.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import com.google.android.material.appbar.AppBarLayout
import com.lowe.wanandroid.BR
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.FragmentProfileBinding
import com.lowe.wanandroid.services.model.UserBaseInfo
import com.lowe.wanandroid.ui.BaseFragment
import com.lowe.wanandroid.ui.login.LoginActivity
import com.lowe.wanandroid.ui.message.MessageActivity
import com.lowe.wanandroid.ui.profile.item.ProfileItemBinder
import com.lowe.wanandroid.utils.ToastEx.showShortToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import okhttp3.internal.immutableListOf
import kotlin.math.abs

@AndroidEntryPoint
class ProfileFragment :
    BaseFragment<ProfileViewModel, FragmentProfileBinding>(R.layout.fragment_profile) {

    private val profileItemAdapter = MultiTypeAdapter()
    private var collapsingToolBarStateFlow =
        MutableStateFlow(ProfileCollapsingToolBarState.EXPANDED)

    override val viewModel: ProfileViewModel by viewModels()

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
            userName.setOnClickListener {
                requireActivity().startActivityFromFragment(
                    this@ProfileFragment,
                    Intent(context, LoginActivity::class.java),
                    LoginActivity.REQUEST_CODE_TO_LOGIN_ACTIVITY
                )
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
                        viewBinding.collapsingToolbarLayout.title =
                            viewBinding.user?.userInfo?.nickname
                    else viewBinding.collapsingToolbarLayout.title = ""
                }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.userBaseInfoStateFlow().collectLatest {
                userInfoGot(it)
            }
        }
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                viewModel.fetchUserInfo()
            }
        }
    }

    private fun onOptionClick(position: Int, item: ProfileItemBean) {
        item.title.showShortToast()
        when (item.title) {
            getString(R.string.profile_item_title_message) -> {
                startActivity(Intent(requireContext(), MessageActivity::class.java))
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

    private fun userInfoGot(response: UserBaseInfo) {
        viewBinding.user = response
        viewBinding.notifyPropertyChanged(BR.user)
    }
}
