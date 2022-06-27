package com.lowe.wanandroid.ui.navigator.child.navigator

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.lowe.multitype.MultiTypeAdapter
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.FragmentNavigatorChildNavigatorBinding
import com.lowe.wanandroid.services.model.Article
import com.lowe.wanandroid.services.model.Navigation
import com.lowe.wanandroid.ui.BaseFragment
import com.lowe.wanandroid.ui.navigator.NavigatorFragment
import com.lowe.wanandroid.ui.navigator.NavigatorTabBean
import com.lowe.wanandroid.ui.navigator.child.navigator.item.NavigatorChildTagChildrenItemBinder
import com.lowe.wanandroid.ui.navigator.widgets.NavigatorTagOnScrollListener
import com.lowe.wanandroid.ui.web.WebActivity
import com.lowe.wanandroid.utils.smoothSnapToPosition
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NavigatorChildFragment :
    BaseFragment<NavigatorChildViewModel, FragmentNavigatorChildNavigatorBinding>(R.layout.fragment_navigator_child_navigator) {

    companion object {
        fun newInstance(navigatorTabBean: NavigatorTabBean): NavigatorChildFragment =
            with(NavigatorChildFragment()) {
                arguments?.apply {
                    putParcelable(
                        NavigatorFragment.KEY_NAVIGATOR_CHILD_HOME_TAB_PARCELABLE,
                        navigatorTabBean
                    )
                }
                this
            }
    }

    private val tagChildrenAdapter = MultiTypeAdapter()
    private val tagOnScrollListener = NavigatorTagOnScrollListener()

    override val viewModel: NavigatorChildViewModel by viewModels()

    override fun init(savedInstanceState: Bundle?) {
        initView()
        initObserve()
    }

    private fun initView() {
        viewBinding.apply {
            with(tagChildrenList) {
                tagChildrenAdapter.register(NavigatorChildTagChildrenItemBinder(this@NavigatorChildFragment::onTagChildrenItemClick))
                adapter = tagChildrenAdapter
                layoutManager = LinearLayoutManager(context)
                addOnScrollListener(tagOnScrollListener)
            }
        }
    }

    private fun initObserve() {
        viewModel.apply {
            navigationTagListLiveData.observe(viewLifecycleOwner) {
                dispatchToAdapter(it, tagChildrenAdapter)
                generateVerticalScrollChipGroup(it.first)
            }
        }
        tagOnScrollListener.firstCompletelyVisiblePosChange.observe(
            viewLifecycleOwner,
            this::tagSelectedChange
        )
    }

    private fun generateVerticalScrollChipGroup(tags: List<Any>) {
        tags.asSequence().mapNotNull {
            it as? Navigation
        }.map {
            Chip(
                this.requireContext(),
                null,
                R.style.Custom_Widget_MaterialComponents_Chip_Choice
            ).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                setChipBackgroundColorResource(R.color.choice_chip_background_color)
                text = it.name
                isCheckable = true
                isCheckedIconVisible = false
                gravity = Gravity.CENTER
                textAlignment = View.TEXT_ALIGNMENT_CENTER
            }
        }.forEach { chip ->
            chip.setOnClickListener {
                onTagClick(
                    viewBinding.verticalTagList.getChipGroup().indexOfChild(it)
                )
            }
            viewBinding.verticalTagList.addOneView(chip)
        }
        viewBinding.verticalTagList.checkByPosition(0)
    }

    private fun dispatchToAdapter(
        result: Pair<List<Any>, DiffUtil.DiffResult>,
        adapter: MultiTypeAdapter
    ) {
        adapter.items = result.first
        result.second.dispatchUpdatesTo(adapter)
    }

    private fun onTagClick(pos: Int) {
        viewBinding.tagChildrenList.smoothSnapToPosition(pos)
    }

    private fun tagSelectedChange(pos: Int) {
        viewBinding.verticalTagList.checkByPosition(pos)
    }

    private fun onTagChildrenItemClick(article: Article) {
        context?.apply {
            WebActivity.loadUrl(this, article.link)
        }
    }
}