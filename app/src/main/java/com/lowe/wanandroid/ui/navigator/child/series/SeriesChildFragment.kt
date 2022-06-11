package com.lowe.wanandroid.ui.navigator.child.series

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.FragmentNavigatorChildSeriesBinding
import com.lowe.wanandroid.services.model.Classify
import com.lowe.wanandroid.services.model.Series
import com.lowe.wanandroid.ui.BaseFragment
import com.lowe.wanandroid.ui.navigator.NavigatorFragment
import com.lowe.wanandroid.ui.navigator.NavigatorTabBean
import com.lowe.wanandroid.ui.navigator.child.navigator.TagSelectedChange
import com.lowe.wanandroid.ui.navigator.child.series.detail.SeriesDetailListActivity
import com.lowe.wanandroid.ui.navigator.child.series.item.SeriesChildTagChildrenItemBinder
import com.lowe.wanandroid.ui.navigator.child.series.item.SeriesChildTagItemBinder
import com.lowe.wanandroid.ui.navigator.widgets.NavigatorTagOnScrollListener
import com.lowe.wanandroid.utils.smoothSnapToPosition

class SeriesChildFragment :
    BaseFragment<SeriesChildViewModel, FragmentNavigatorChildSeriesBinding>(R.layout.fragment_navigator_child_series) {

    companion object {
        fun newInstance(navigatorTabBean: NavigatorTabBean): SeriesChildFragment = with(
            SeriesChildFragment()
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

    private val tagAdapter = MultiTypeAdapter()
    private val tagChildrenAdapter = MultiTypeAdapter()
    private val tagOnScrollListener = NavigatorTagOnScrollListener()

    override fun createViewModel() = SeriesChildViewModel()

    override fun init(savedInstanceState: Bundle?) {
        initView()
        initObserve()
        onRefresh()
    }

    private fun initView() {
        viewBinding.apply {
            with(seriesTagList) {
                tagAdapter.register(SeriesChildTagItemBinder(this@SeriesChildFragment::onTagClick))
                adapter = tagAdapter
                layoutManager = LinearLayoutManager(context)
            }
            with(seriesTagChildrenList) {
                tagChildrenAdapter.register(SeriesChildTagChildrenItemBinder(this@SeriesChildFragment::onTagChildrenItemClick))
                adapter = tagChildrenAdapter
                layoutManager = LinearLayoutManager(context)
                addOnScrollListener(tagOnScrollListener)
            }
        }
    }

    private fun initObserve() {
        viewModel.apply {
            seriesListLiveData.observe(viewLifecycleOwner) {
                dispatchToAdapter(it, tagAdapter)
                dispatchToAdapter(it, tagChildrenAdapter)
            }
        }
        tagOnScrollListener.firstCompletelyVisiblePosChange.observe(viewLifecycleOwner) { pos ->
            val item = tagChildrenAdapter.items[pos] as? Series ?: return@observe
            if (item.isSelected.not()) tagSelectedChange(pos)
        }
    }

    private fun dispatchToAdapter(
        result: Pair<List<Any>, DiffUtil.DiffResult>,
        adapter: MultiTypeAdapter
    ) {
        adapter.items = result.first
        result.second.dispatchUpdatesTo(adapter)
    }

    private fun onRefresh() {
        viewModel.fetchSeriesList()
    }

    private fun onTagClick(action: Pair<Int, Series>) {
        val (pos, _) = action
        viewBinding.seriesTagChildrenList.smoothSnapToPosition(pos)
        tagSelectedChange(pos)
    }

    private fun onTagChildrenItemClick(classify: Classify) {
        val series = viewModel.getCurrentList()
            .find { it is Series && it.id == classify.parentChapterId } as? Series ?: return
        startActivity(Intent(this.context, SeriesDetailListActivity::class.java).apply {
            putParcelableArrayListExtra(
                SeriesDetailListActivity.KEY_BUNDLE_CLASSIFY_LIST_TAB,
                series.children as ArrayList<out Parcelable>
            )
        })
    }

    private fun tagSelectedChange(pos: Int) {
        //TODO ugly code
        viewModel.getCurrentList().forEachIndexed { index, any ->
            if (any is Series && any.isSelected) {
                any.isSelected = false
                tagAdapter.notifyItemChanged(index, TagSelectedChange)
            }
        }
        (viewModel.getCurrentList()[pos] as Series).isSelected = true
        tagAdapter.notifyItemChanged(pos, TagSelectedChange)
        viewBinding.seriesTagList.post {
            viewBinding.seriesTagList.scrollToPosition(pos)
        }
    }
}