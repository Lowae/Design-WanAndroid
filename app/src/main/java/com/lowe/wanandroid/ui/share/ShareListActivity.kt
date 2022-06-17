package com.lowe.wanandroid.ui.share

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.lowe.multitype.MultiTypeAdapter
import com.google.android.material.appbar.AppBarLayout
import com.lowe.wanandroid.BR
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.ActivityShareListBinding
import com.lowe.wanandroid.services.model.Article
import com.lowe.wanandroid.services.model.ShareBean
import com.lowe.wanandroid.ui.BaseActivity
import com.lowe.wanandroid.ui.home.item.HomeArticleItemBinder
import com.lowe.wanandroid.ui.profile.ProfileCollapsingToolBarState
import com.lowe.wanandroid.ui.web.WebActivity
import com.lowe.wanandroid.utils.loadMore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlin.math.abs

@AndroidEntryPoint
class ShareListActivity :
    BaseActivity<ShareListViewModel, ActivityShareListBinding>(R.layout.activity_share_list) {

    private val shareAdapter = MultiTypeAdapter()
    private var collapsingToolBarStateFlow =
        MutableStateFlow(ProfileCollapsingToolBarState.EXPANDED)

    override val viewModel: ShareListViewModel by viewModels()

    override fun init(savedInstanceState: Bundle?) {
        initView()
        initObserve()
    }

    private fun initView() {
        shareAdapter.register(HomeArticleItemBinder(this::onArticleClick))
        viewDataBinding.apply {
            with(shareList) {
                adapter = shareAdapter
                layoutManager = LinearLayoutManager(context)
                loadMore(loadFinish = { viewModel.isLoading.not() }) {
                    viewModel.fetchShareList()
                }
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
        viewModel.apply {
            shareArticlesLiveData.observe(this@ShareListActivity) {
                updateShareUserInfo(it.first)
                dispatchToAdapter(it.first.shareArticles.datas to it.second, shareAdapter)
            }
        }
    }

    private fun updateShareUserInfo(shareBean: ShareBean) {
        viewDataBinding.shareBean = shareBean
        viewDataBinding.notifyPropertyChanged(BR.shareBean)
    }

    private fun dispatchToAdapter(
        result: Pair<List<Any>, DiffUtil.DiffResult>,
        adapter: MultiTypeAdapter
    ) {
        adapter.items = result.first
        result.second.dispatchUpdatesTo(adapter)
    }

    private fun onArticleClick(position: Int, article: Article) {
        WebActivity.loadUrl(this, article.link)
    }

}