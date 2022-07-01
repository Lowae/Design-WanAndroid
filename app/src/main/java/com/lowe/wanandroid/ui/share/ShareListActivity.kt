package com.lowe.wanandroid.ui.share

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.lowe.multitype.MultiTypePagingAdapter
import com.lowe.wanandroid.BR
import com.lowe.wanandroid.R
import com.lowe.wanandroid.base.app.AppViewModel
import com.lowe.wanandroid.databinding.ActivityShareListBinding
import com.lowe.wanandroid.services.model.Article
import com.lowe.wanandroid.services.model.CollectEvent
import com.lowe.wanandroid.services.model.ShareBean
import com.lowe.wanandroid.ui.ActivityDataBindingDelegate
import com.lowe.wanandroid.ui.ArticleDiffCalculator
import com.lowe.wanandroid.ui.BaseActivity
import com.lowe.wanandroid.ui.home.item.ArticleAction
import com.lowe.wanandroid.ui.home.item.HomeArticleItemBinderV2
import com.lowe.wanandroid.ui.profile.ProfileCollapsingToolBarState
import com.lowe.wanandroid.ui.web.WebActivity
import com.lowe.wanandroid.utils.Activities
import com.lowe.wanandroid.utils.showShortToast
import com.lowe.wanandroid.utils.isEmpty
import com.lowe.wanandroid.utils.isRefreshing
import com.lowe.wanandroid.utils.whenError
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject
import kotlin.math.abs

@AndroidEntryPoint
class ShareListActivity : BaseActivity<ShareListViewModel, ActivityShareListBinding>() {

    @Inject
    lateinit var appViewModel: AppViewModel

    private val userId by lazy(LazyThreadSafetyMode.NONE) {
        intent.getStringExtra(Activities.ShareList.KEY_SHARE_LIST_USER_ID) ?: ""
    }

    private val shareAdapter =
        MultiTypePagingAdapter(ArticleDiffCalculator.getCommonArticleDiffItemCallback()).apply {
            register(HomeArticleItemBinderV2(this@ShareListActivity::onArticleClick))
        }

    private var collapsingToolBarStateFlow =
        MutableStateFlow(ProfileCollapsingToolBarState.EXPANDED)

    override val viewDataBinding: ActivityShareListBinding by ActivityDataBindingDelegate(R.layout.activity_share_list)

    override val viewModel: ShareListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initObserve()
    }

    private fun initView() {
        viewDataBinding.apply {
            with(shareList) {
                adapter = shareAdapter
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
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
            with(toolbar) {
                setNavigationOnClickListener {
                    finish()
                }
            }
        }
    }

    private fun initObserve() {
        lifecycleScope.launchWhenCreated {
            collapsingToolBarStateFlow
                .distinctUntilChanged { old, new ->
                    old == new
                }.collectLatest {
                    if (it == ProfileCollapsingToolBarState.COLLAPSED)
                        viewDataBinding.collapsingToolbarLayout.title =
                            viewDataBinding.shareBean?.coinInfo?.nickname
                    else viewDataBinding.collapsingToolbarLayout.title = ""
                }
        }
        lifecycleScope.launchWhenCreated {
            shareAdapter.loadStateFlow.collectLatest { loadState ->
                loadState.whenError { it.error.message?.showShortToast() }
                updateLoadStates(loadState)
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.getShareFlow(userId).collectLatest(shareAdapter::submitData)
        }
        lifecycleScope.launchWhenCreated {
            viewModel.getShareBeanFlow().collectLatest(this@ShareListActivity::updateShareUserInfo)
        }
        appViewModel.collectArticleEvent.observe(this) { event ->
            shareAdapter.snapshot().run {
                val index = indexOfFirst { it is Article && it.id == event.id }
                if (index >= 0) {
                    (this[index] as? Article)?.collect = event.isCollected
                    index
                } else null
            }?.apply(shareAdapter::notifyItemChanged)
        }
    }

    private fun updateShareUserInfo(shareBean: ShareBean) {
        viewDataBinding.shareBean = shareBean
        viewDataBinding.notifyPropertyChanged(BR.shareBean)
    }

    private fun onArticleClick(articleAction: ArticleAction) {
        when (articleAction) {
            is ArticleAction.ItemClick -> WebActivity.loadUrl(
                this, Activities.Web.WebIntent(
                    articleAction.article.link,
                    articleAction.article.id,
                    articleAction.article.collect,
                )
            )
            is ArticleAction.CollectClick -> {
                appViewModel.articleCollectAction(
                    CollectEvent(
                        articleAction.article.id,
                        articleAction.article.link,
                        articleAction.article.collect.not()
                    )
                )
            }
            else -> {}
        }
    }

    private fun updateLoadStates(loadStates: CombinedLoadStates) {
        viewDataBinding.loadingContainer.apply {
            emptyLayout.isVisible =
                loadStates.refresh is LoadState.NotLoading && shareAdapter.isEmpty()
            loadingProgress.isVisible = shareAdapter.isEmpty() && loadStates.isRefreshing
        }
    }
}