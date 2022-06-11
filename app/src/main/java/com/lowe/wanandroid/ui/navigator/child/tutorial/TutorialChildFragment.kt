package com.lowe.wanandroid.ui.navigator.child.tutorial

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.FragmentNavigatorChildTutorialBinding
import com.lowe.wanandroid.services.model.Classify
import com.lowe.wanandroid.ui.BaseFragment
import com.lowe.wanandroid.ui.navigator.NavigatorFragment
import com.lowe.wanandroid.ui.navigator.NavigatorTabBean
import com.lowe.wanandroid.ui.navigator.child.tutorial.item.TutorialChildItemBinder
import com.lowe.wanandroid.ui.navigator.child.tutorial.list.TutorialChapterListActivity

class TutorialChildFragment :
    BaseFragment<TutorialChildViewModel, FragmentNavigatorChildTutorialBinding>(R.layout.fragment_navigator_child_tutorial) {

    companion object {
        fun newInstance(navigatorTabBean: NavigatorTabBean): TutorialChildFragment = with(
            TutorialChildFragment()
        ) {
            arguments?.apply {
                putParcelable(
                    NavigatorFragment.KEY_NAVIGATOR_CHILD_HOME_TAB_PARCELABLE,
                    navigatorTabBean
                )
            }
            this
        }
    }

    private val tutorialAdapter = MultiTypeAdapter()

    override fun createViewModel() = TutorialChildViewModel()

    override fun init(savedInstanceState: Bundle?) {
        initView()
        initObserve()
    }

    private fun initView() {
        viewBinding.apply {
            with(tutorialList) {
                tutorialAdapter.register(TutorialChildItemBinder(this@TutorialChildFragment::onTutorialItemClick))
                adapter = tutorialAdapter
                layoutManager = LinearLayoutManager(context)
            }
        }
    }

    private fun initObserve() {
        viewModel.apply {
            tutorialListLiveData.observe(
                viewLifecycleOwner,
                this@TutorialChildFragment::dispatchToAdapter
            )
        }
    }

    private fun dispatchToAdapter(result: Pair<List<Any>, DiffUtil.DiffResult>) {
        tutorialAdapter.items = result.first
        result.second.dispatchUpdatesTo(tutorialAdapter)
    }

    private fun onTutorialItemClick(action: Pair<Int, Classify>) {
        val (pos, classify) = action
        startActivity(Intent(this.context, TutorialChapterListActivity::class.java).apply {
            putExtra(TutorialChapterListActivity.KEY_INTENT_TUTORIAL_ID, classify.id)
        })
    }
}