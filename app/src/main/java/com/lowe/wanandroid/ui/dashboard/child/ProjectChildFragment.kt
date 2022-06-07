package com.lowe.wanandroid.ui.dashboard.child

import android.os.Bundle
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.FragmentChildProjectBinding
import com.lowe.wanandroid.ui.BaseFragment

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

    override fun createViewModel() = ProjectChildViewModel()

    override fun init(savedInstanceState: Bundle?) {
        viewBinding.testId.text = arguments?.getInt(KEY_PROJECT_CHILD_CATEGORY_ID, -1).toString()
    }
}