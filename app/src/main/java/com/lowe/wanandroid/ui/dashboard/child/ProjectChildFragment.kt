package com.lowe.wanandroid.ui.dashboard.child

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.FragmentChildProjectBinding
import com.lowe.wanandroid.ui.BaseFragment
import com.lowe.wanandroid.ui.dashboard.ProjectViewModel
import com.lowe.wanandroid.ui.dashboard.child.item.ProjectChildItemBinder
import com.lowe.wanandroid.utils.loadMore

class ProjectChildFragment :
    BaseFragment<ProjectChildViewModel, FragmentChildProjectBinding>(R.layout.fragment_child_project) {
    companion object {

        const val KEY_PROJECT_CHILD_CATEGORY_ID = "key_project_child_category_id"

        fun newInstance(categoryId: Int) = ProjectChildFragment().apply {
            arguments = with(Bundle()) {
                putInt(KEY_PROJECT_CHILD_CATEGORY_ID, categoryId)
                this
            }
        }

    }

    private val projectAdapter = MultiTypeAdapter()
    private val projectViewModel by viewModels<ProjectViewModel>(this::requireParentFragment)
    private val categoryId by lazy { arguments?.getInt(KEY_PROJECT_CHILD_CATEGORY_ID, -1) ?: -1 }

    override fun createViewModel() = ProjectChildViewModel()

    override fun init(savedInstanceState: Bundle?) {
        initView()
        initObserve()
        onRefresh()
    }

    private fun initView() {
        projectAdapter.register(ProjectChildItemBinder())
        viewBinding.apply {
            with(childList) {
                layoutManager = LinearLayoutManager(context)
                adapter = projectAdapter
                loadMore(loadFinish = { viewModel.isLoading.not() }) {
                    viewModel.fetchProjectList(categoryId)
                }
            }
        }
    }

    private fun initObserve() {
        viewModel.projectListLiveData.observe(viewLifecycleOwner, this::dispatchToAdapter)
        projectViewModel.parentRefreshLiveData.observe(viewLifecycleOwner, this::onParentRefresh)
    }

    private fun dispatchToAdapter(result: Pair<List<Any>, DiffUtil.DiffResult>) {
        projectAdapter.items = result.first
        result.second.dispatchUpdatesTo(projectAdapter)
    }

    private fun onParentRefresh(categoryId: Int) {
        if (categoryId != this.categoryId) return
        onRefresh()
    }

    private fun onRefresh() {
        viewModel.fetchProjectList(categoryId, true)
    }
}