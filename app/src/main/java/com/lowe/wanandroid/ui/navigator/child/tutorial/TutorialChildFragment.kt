package com.lowe.wanandroid.ui.navigator.child.tutorial

import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.lowe.multitype.MultiTypeAdapter
import com.lowe.wanandroid.R
import com.lowe.wanandroid.compat.BundleCompat
import com.lowe.wanandroid.databinding.FragmentNavigatorChildTutorialBinding
import com.lowe.wanandroid.services.model.Classify
import com.lowe.wanandroid.ui.BaseFragment
import com.lowe.wanandroid.ui.navigator.NavigatorChildFragmentAdapter
import com.lowe.wanandroid.ui.navigator.NavigatorFragment
import com.lowe.wanandroid.ui.navigator.NavigatorTabBean
import com.lowe.wanandroid.ui.navigator.child.tutorial.item.TutorialChildItemBinder
import com.lowe.wanandroid.ui.navigator.child.tutorial.list.TutorialChapterListActivity
import com.lowe.wanandroid.utils.unsafeLazy
import dagger.hilt.android.AndroidEntryPoint

/**
 * 教程子Fragment页面
 */
@AndroidEntryPoint
class TutorialChildFragment :
    BaseFragment<TutorialChildViewModel, FragmentNavigatorChildTutorialBinding>(R.layout.fragment_navigator_child_tutorial) {

    companion object {
        fun newInstance(navigatorTabBean: NavigatorTabBean): TutorialChildFragment = with(
            TutorialChildFragment()
        ) {
            arguments =
                bundleOf(NavigatorFragment.KEY_NAVIGATOR_CHILD_HOME_TAB_PARCELABLE to navigatorTabBean)
            this
        }
    }

    private val navigatorTabBean by unsafeLazy {
        BundleCompat.getParcelable(
            arguments,
            NavigatorFragment.KEY_NAVIGATOR_CHILD_HOME_TAB_PARCELABLE
        ) ?: NavigatorTabBean(NavigatorChildFragmentAdapter.NAVIGATOR_TAB_TUTORIAL)
    }
    private val tutorialAdapter = MultiTypeAdapter()
    override val viewModel: TutorialChildViewModel by viewModels()

    override fun onViewCreated(savedInstanceState: Bundle?) {
        initView()
        initEvents()
    }

    private fun initView() {
        viewDataBinding.apply {
            with(tutorialList) {
                tutorialAdapter.register(TutorialChildItemBinder(this@TutorialChildFragment::onTutorialItemClick))
                adapter = tutorialAdapter
                layoutManager = LinearLayoutManager(context)
            }
        }
    }

    private fun initEvents() {
        viewModel.apply {
            tutorialListLiveData.observe(viewLifecycleOwner) {
                dispatchToAdapter(it)
                viewDataBinding.loadingContainer.loadingProgress.isVisible = false
            }
        }
    }

    private fun dispatchToAdapter(result: Pair<List<Any>, DiffUtil.DiffResult>) {
        tutorialAdapter.items = result.first
        result.second.dispatchUpdatesTo(tutorialAdapter)
    }

    private fun onTutorialItemClick(position: Int, classify: Classify) {
        startActivity(Intent(this.context, TutorialChapterListActivity::class.java).apply {
            putExtra(TutorialChapterListActivity.KEY_INTENT_TUTORIAL_TITLE, classify.name)
            putExtra(TutorialChapterListActivity.KEY_INTENT_TUTORIAL_ID, classify.id)
        })
    }
}