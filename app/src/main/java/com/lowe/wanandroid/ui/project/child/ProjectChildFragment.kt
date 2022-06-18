package com.lowe.wanandroid.ui.project.child

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.lowe.multitype.paging.MultiTypePagingAdapter
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.FragmentChildProjectBinding
import com.lowe.wanandroid.services.model.Article
import com.lowe.wanandroid.ui.ArticleDiffCalculator
import com.lowe.wanandroid.ui.BaseFragment
import com.lowe.wanandroid.ui.project.ProjectViewModel
import com.lowe.wanandroid.ui.project.child.item.ProjectChildItemBinder
import com.lowe.wanandroid.ui.web.WebActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ProjectChildFragment :
    BaseFragment<ProjectChildViewModel, FragmentChildProjectBinding>(R.layout.fragment_child_project) {

    companion object {
        const val CATEGORY_ID_NEWEST_PROJECT = 0
        const val KEY_PROJECT_CHILD_CATEGORY_ID = "key_project_child_category_id"

        fun newInstance(categoryId: Int) = ProjectChildFragment().apply {
            arguments = with(Bundle()) {
                putInt(KEY_PROJECT_CHILD_CATEGORY_ID, categoryId)
                this
            }
        }

    }

    private val projectAdapter =
        MultiTypePagingAdapter(ArticleDiffCalculator.getCommonArticleDiffItemCallback()).apply {
            register(ProjectChildItemBinder(this@ProjectChildFragment::onItemClick))
        }
    private val projectViewModel by viewModels<ProjectViewModel>(this::requireParentFragment)
    private val categoryId by lazy { arguments?.getInt(KEY_PROJECT_CHILD_CATEGORY_ID, -1) ?: -1 }

    override val viewModel: ProjectChildViewModel by viewModels()

    override fun init(savedInstanceState: Bundle?) {
        initView()
        initEvents()
    }

    private fun initView() {
        viewBinding.apply {
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
        projectViewModel.parentRefreshLiveData.observe(viewLifecycleOwner, this::onParentRefresh)
        projectViewModel.scrollToTopLiveData.observe(viewLifecycleOwner, this::scrollToTop)
    }

    private fun onParentRefresh(categoryId: Int) {
        if (categoryId != this.categoryId) return
        projectAdapter.refresh()
    }

    private fun scrollToTop(categoryId: Int) {
        if (categoryId != this.categoryId) return
        viewBinding.childList.scrollToPosition(0)
    }

    private fun onItemClick(position: Int, article: Article) {
        WebActivity.loadUrl(this.requireContext(), article.link)
    }
}