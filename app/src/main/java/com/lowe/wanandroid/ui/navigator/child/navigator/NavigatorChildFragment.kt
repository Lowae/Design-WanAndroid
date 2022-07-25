package com.lowe.wanandroid.ui.navigator.child.navigator

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.lowe.wanandroid.BaseFragment
import com.lowe.common.services.model.Article
import com.lowe.common.services.model.Navigation
import com.lowe.common.utils.Activities
import com.lowe.common.utils.smoothSnapToPosition
import com.lowe.multitype.MultiTypeAdapter
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.FragmentNavigatorChildNavigatorBinding
import com.lowe.wanandroid.ui.navigator.NavigatorFragment
import com.lowe.wanandroid.ui.navigator.NavigatorTabBean
import com.lowe.wanandroid.ui.navigator.child.navigator.item.NavigatorChildTagChildrenItemBinder
import com.lowe.wanandroid.ui.navigator.widgets.NavigatorTagOnScrollListener
import com.lowe.wanandroid.ui.web.WebActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * 导航子Fragment页面
 */
@AndroidEntryPoint
class NavigatorChildFragment :
    BaseFragment<NavigatorChildViewModel, FragmentNavigatorChildNavigatorBinding>(R.layout.fragment_navigator_child_navigator) {

    companion object {
        fun newInstance(navigatorTabBean: NavigatorTabBean): NavigatorChildFragment =
            with(NavigatorChildFragment()) {
                arguments =
                    bundleOf(NavigatorFragment.KEY_NAVIGATOR_CHILD_HOME_TAB_PARCELABLE to navigatorTabBean)
                this
            }
    }

    private val tagChildrenAdapter = MultiTypeAdapter()
    private val tagOnScrollListener = NavigatorTagOnScrollListener()

    /**
     * 延迟到verticalTagList已经加载完成后再启动协程
     */
    private val tagChildrenFirstCompletelyVisiblePosChange: Job =
        lifecycleScope.launch(start = CoroutineStart.LAZY) {
            tagOnScrollListener.firstCompletelyVisiblePosChange.collect(this@NavigatorChildFragment::tagSelectedChange)
        }
    override val viewModel: NavigatorChildViewModel by viewModels()

    override fun onViewCreated(savedInstanceState: Bundle?) {
        initView()
        initEvents()
    }

    private fun initView() {
        viewDataBinding.apply {
            with(tagChildrenList) {
                tagChildrenAdapter.register(NavigatorChildTagChildrenItemBinder(this@NavigatorChildFragment::onTagChildrenItemClick))
                adapter = tagChildrenAdapter
                layoutManager = LinearLayoutManager(context)
                addOnScrollListener(tagOnScrollListener)
            }
        }
    }

    private fun initEvents() {
        viewModel.apply {
            navigationTagListLiveData.observe(viewLifecycleOwner) {
                dispatchToAdapter(it, tagChildrenAdapter)
                generateVerticalScrollChipGroup(it.first)
                if (tagChildrenFirstCompletelyVisiblePosChange.isActive.not()) {
                    tagChildrenFirstCompletelyVisiblePosChange.start()
                }
                viewDataBinding.loadingContainer.loadingProgress.isVisible = false
            }
        }
    }

    /**
     * 生成竖向列表Chip
     */
    private fun generateVerticalScrollChipGroup(tags: List<Any>) {
        viewDataBinding.verticalTagList.setViews(
            tags.map {
                val navigation = it as Navigation
                Chip(
                    this.requireContext(),
                    null,
                    com.google.android.material.R.style.Widget_MaterialComponents_Chip_Choice
                ).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    setChipBackgroundColorResource(R.color.choice_chip_background_color)
                    text = navigation.name
                    textSize = 13F
                    chipStartPadding = 0F
                    chipEndPadding = 0F
                    textStartPadding = 0F
                    textEndPadding = 0F
                    isCheckable = true
                    isCheckedIconVisible = false
                    gravity = Gravity.CENTER
                    textAlignment = View.TEXT_ALIGNMENT_CENTER
                    if (paint.measureText(text.toString()) > viewDataBinding.verticalTagList.width) {
                        textSize = 11F
                    }
                }
            }.onEach { chip ->
                chip.setOnClickListener {
                    onTagClick(
                        viewDataBinding.verticalTagList.getChipGroup().indexOfChild(it)
                    )
                }
            }
        )
    }

    private fun dispatchToAdapter(
        result: Pair<List<Any>, DiffUtil.DiffResult>,
        adapter: MultiTypeAdapter
    ) {
        adapter.items = result.first
        result.second.dispatchUpdatesTo(adapter)
    }

    private fun onTagClick(pos: Int) {
        viewDataBinding.tagChildrenList.smoothSnapToPosition(pos)
    }

    private fun tagSelectedChange(pos: Int) {
        viewDataBinding.verticalTagList.checkByPosition(pos)
    }

    private fun onTagChildrenItemClick(article: Article) {
        context?.apply {
            WebActivity.loadUrl(
                this, Activities.Web.WebIntent(article.link)
            )
        }
    }
}