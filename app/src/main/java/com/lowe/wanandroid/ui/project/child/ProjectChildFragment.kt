package com.lowe.wanandroid.ui.project.child

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.lowe.multitype.MultiTypePagingAdapter
import com.lowe.wanandroid.R
import com.lowe.wanandroid.base.app.AppViewModel
import com.lowe.wanandroid.databinding.FragmentChildProjectBinding
import com.lowe.wanandroid.services.model.Article
import com.lowe.wanandroid.services.model.CollectEvent
import com.lowe.wanandroid.ui.ArticleDiffCalculator
import com.lowe.wanandroid.ui.BaseFragment
import com.lowe.wanandroid.ui.LoadMoreItemBinder
import com.lowe.wanandroid.ui.home.item.ArticleAction
import com.lowe.wanandroid.ui.project.ProjectViewModel
import com.lowe.wanandroid.ui.project.child.item.ProjectChildItemBinder
import com.lowe.wanandroid.ui.web.WebActivity
import com.lowe.wanandroid.utils.Activities
import com.lowe.wanandroid.utils.isEmpty
import com.lowe.wanandroid.utils.isRefreshing
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class ProjectChildFragment :
    BaseFragment<ProjectChildViewModel, FragmentChildProjectBinding>(R.layout.fragment_child_project) {

    companion object {
        const val CATEGORY_ID_NEWEST_PROJECT = 0
        const val KEY_PROJECT_CHILD_CATEGORY_ID = "key_project_child_category_id"

        fun newInstance(categoryId: Int) = ProjectChildFragment().apply {
            arguments = bundleOf(KEY_PROJECT_CHILD_CATEGORY_ID to categoryId)
        }
    }

    @Inject
    lateinit var appViewModel: AppViewModel

    private val projectAdapter =
        MultiTypePagingAdapter(ArticleDiffCalculator.getCommonDiffItemCallback()).apply {
            register(ProjectChildItemBinder(this@ProjectChildFragment::onItemClick))
            registerFooter(LoadMoreItemBinder())
        }
    private val projectViewModel by viewModels<ProjectViewModel>(this::requireParentFragment)
    private val categoryId by lazy { arguments?.getInt(KEY_PROJECT_CHILD_CATEGORY_ID, -1) ?: -1 }

    override val viewModel: ProjectChildViewModel by viewModels()

    override fun onViewCreated(savedInstanceState: Bundle?) {
        initView()
        initEvents()
    }

    private fun initView() {
        viewDataBinding.apply {
            with(childList) {
                layoutManager = LinearLayoutManager(context)
                adapter = projectAdapter
                setHasFixedSize(true)
            }
        }
    }

    private fun initEvents() {
        lifecycleScope.launchWhenCreated {
            viewModel.getProjectListFlow(categoryId).collectLatest(projectAdapter::submitData)
        }
        lifecycleScope.launchWhenCreated {
            projectAdapter.loadStateFlow.collect(this@ProjectChildFragment::updateLoadStates)
        }
        projectViewModel.parentRefreshLiveData.observe(viewLifecycleOwner, this::onParentRefresh)
        projectViewModel.scrollToTopLiveData.observe(viewLifecycleOwner, this::scrollToTop)
        appViewModel.collectArticleEvent.observe(viewLifecycleOwner, this::refreshCollectStatus)
    }

    private fun onParentRefresh(categoryId: Int) {
        if (categoryId != this.categoryId) return
        projectAdapter.refresh()
    }

    private fun scrollToTop(categoryId: Int) {
        if (categoryId != this.categoryId) return
        viewDataBinding.childList.scrollToPosition(0)
    }

    private fun refreshCollectStatus(event: CollectEvent) {
        projectAdapter.snapshot().run {
            val index = indexOfFirst { it is Article && it.id == event.id }
            if (index >= 0) {
                (this[index] as? Article)?.collect = event.isCollected
                index
            } else null
        }?.apply(projectAdapter::notifyItemChanged)
    }

    private fun onItemClick(articleAction: ArticleAction) {
        when (articleAction) {
            is ArticleAction.ItemClick -> WebActivity.loadUrl(
                requireContext(),
                Activities.Web.WebIntent(
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
                loadStates.refresh is LoadState.NotLoading && projectAdapter.isEmpty()
            loadingProgress.isVisible = projectAdapter.isEmpty() && loadStates.isRefreshing
        }
    }
}