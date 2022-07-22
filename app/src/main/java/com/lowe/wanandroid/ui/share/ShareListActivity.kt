package com.lowe.wanandroid.ui.share

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.lowe.multitype.PagingMultiTypeAdapter
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
import com.lowe.wanandroid.ui.profile.CollapsingToolBarState
import com.lowe.wanandroid.ui.web.WebActivity
import com.lowe.wanandroid.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.abs

/**
 * 分享文章页
 */
@AndroidEntryPoint
class ShareListActivity : BaseActivity<ShareListViewModel, ActivityShareListBinding>() {

    @Inject
    lateinit var appViewModel: AppViewModel

    private val userId by unsafeLazy {
        intent.getStringExtra(Activities.ShareList.KEY_SHARE_LIST_USER_ID) ?: ""
    }

    private val shareAdapter =
        PagingMultiTypeAdapter(ArticleDiffCalculator.getCommonDiffItemCallback()).apply {
            register(HomeArticleItemBinderV2(this@ShareListActivity::onArticleClick))
        }

    private var collapsingToolBarStateFlow =
        MutableStateFlow(CollapsingToolBarState.EXPANDED)

    override val viewDataBinding: ActivityShareListBinding by ActivityDataBindingDelegate(R.layout.activity_share_list)

    override val viewModel: ShareListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initEvents()
        viewModel.fetch(userId)
    }

    private fun initView() {
        viewDataBinding.apply {
            with(shareList) {
                adapter = shareAdapter
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
            }
            with(appBarLayout) {
                addOnOffsetChangedListener { appBarLayout, verticalOffset ->
                    when {
                        verticalOffset == 0 -> collapsingToolBarStateFlow.value =
                            CollapsingToolBarState.EXPANDED
                        abs(verticalOffset) >= appBarLayout.totalScrollRange -> collapsingToolBarStateFlow.value =
                            CollapsingToolBarState.COLLAPSED
                        else -> collapsingToolBarStateFlow.value =
                            CollapsingToolBarState.INTERMEDIATE
                    }
                }
            }
            with(toolbar) {
                setNavigationOnClickListener {
                    finish()
                }
            }
        }
    }

    private fun initEvents() {
        launchRepeatOnStarted {
            launch {
                collapsingToolBarStateFlow
                    .distinctUntilChanged { old, new ->
                        old == new
                    }.collectLatest {
                        if (it == CollapsingToolBarState.COLLAPSED)
                            viewDataBinding.collapsingToolbarLayout.title =
                                viewDataBinding.shareBean?.coinInfo?.nickname
                        else viewDataBinding.collapsingToolbarLayout.title = ""
                    }
            }

            launch {
                shareAdapter.loadStateFlow.collectLatest { loadState ->
                    loadState.whenError { it.error.message?.showShortToast() }
                    updateLoadStates(loadState)
                }
            }

            launch {
                viewModel.shareListFlow.collectLatest(shareAdapter::submitData)
            }

            launch {
                viewModel.getShareBeanFlow.collectLatest(this@ShareListActivity::updateShareUserInfo)
            }
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