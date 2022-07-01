package com.lowe.wanandroid.ui.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.lowe.multitype.MultiTypeAdapter
import com.lowe.wanandroid.BR
import com.lowe.wanandroid.R
import com.lowe.wanandroid.account.AccountManager
import com.lowe.wanandroid.account.AccountState
import com.lowe.wanandroid.account.checkLogin
import com.lowe.wanandroid.databinding.FragmentProfileBinding
import com.lowe.wanandroid.services.model.UserBaseInfo
import com.lowe.wanandroid.ui.BaseFragment
import com.lowe.wanandroid.ui.collect.CollectActivity
import com.lowe.wanandroid.ui.message.MessageActivity
import com.lowe.wanandroid.ui.profile.item.ProfileItemBinder
import com.lowe.wanandroid.ui.tools.ToolListActivity
import com.lowe.wanandroid.ui.web.WebActivity
import com.lowe.wanandroid.utils.Activities
import com.lowe.wanandroid.utils.intentTo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
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

    override fun onViewCreated(savedInstanceState: Bundle?) {
        initView()
        initItems()
        initEvents()
    }

    private fun initView() {
        profileItemAdapter.register(ProfileItemBinder(this::onOptionClick))
        viewDataBinding.apply {
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
            settingFabIcon.setOnClickListener {
                startActivity(intentTo(Activities.Setting))
            }
            arrayOf(userAvatar, userName, userId, userCoinCount).forEach {
                it.setOnClickListener {
                    this@ProfileFragment.viewModel.userStatusFlow().value.checkLogin(requireContext()) {}
                }
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


    private fun initEvents() {
        lifecycleScope.launchWhenCreated {
            collapsingToolBarStateFlow
                .distinctUntilChanged { old, new ->
                    old == new
                }.collectLatest {
                    if (it == ProfileCollapsingToolBarState.COLLAPSED) {
                        viewDataBinding.collapsingToolbarLayout.title =
                            viewDataBinding.user?.userInfo?.nickname
                    } else viewDataBinding.collapsingToolbarLayout.title = ""
                }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.userStatusFlow().collect {
                Log.d("userStatusFlow", it.toString())
                when (it) {
                    is AccountState.LogIn -> {
                        viewModel.fetchUserInfo()
                    }
                    AccountState.LogOut -> {

                    }
                }
            }
        }
        lifecycleScope.launchWhenCreated {
            AccountManager.collectUserInfoFlow().collect(this@ProfileFragment::userInfoGot)
        }
    }

    private fun onOptionClick(position: Int, item: ProfileItemBean) {
        when (item.title) {
            getString(R.string.profile_item_title_message) -> {
                viewModel.userStatusFlow().value.checkLogin(requireContext()) {
                    startActivity(Intent(requireContext(), MessageActivity::class.java))
                }
            }
            getString(R.string.profile_item_title_share) -> {
                viewModel.userStatusFlow().value.checkLogin(requireContext()) {
                    startActivity(
                        intentTo(
                            Activities.ShareList(
                                bundle = bundleOf(Activities.ShareList.KEY_SHARE_LIST_USER_ID to AccountManager.peekUserBaseInfo().userInfo.id)
                            )
                        )
                    )
                }
            }
            getString(R.string.profile_item_title_favorite) -> {
                viewModel.userStatusFlow().value.checkLogin(requireContext()) {
                    startActivity(Intent(requireContext(), CollectActivity::class.java))
                }
            }
            getString(R.string.profile_item_title_tools) -> {
                startActivity(Intent(requireContext(), ToolListActivity::class.java))
            }
            getString(R.string.profile_item_title_project_page) -> {
                WebActivity.loadUrl(requireContext(), "https://github.com/Lowae/WanAndroid")
            }
        }
    }

    private fun userInfoGot(response: UserBaseInfo) {
        viewDataBinding.user = response
        viewDataBinding.notifyPropertyChanged(BR.user)
    }
}
