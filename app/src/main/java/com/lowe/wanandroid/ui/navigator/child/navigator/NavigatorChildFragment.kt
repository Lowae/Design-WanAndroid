package com.lowe.wanandroid.ui.navigator.child.navigator

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.FragmentNavigatorChildNavigatorBinding
import com.lowe.wanandroid.services.model.Navigation
import com.lowe.wanandroid.ui.BaseFragment
import com.lowe.wanandroid.ui.navigator.NavigatorFragment
import com.lowe.wanandroid.ui.navigator.NavigatorTabBean
import com.lowe.wanandroid.ui.navigator.child.navigator.item.NavigatorChildTagChildrenItemBinder
import com.lowe.wanandroid.ui.navigator.child.navigator.item.NavigatorChildTagItemBinder
import com.lowe.wanandroid.utils.smoothSnapToPosition

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

    private val tagAdapter = MultiTypeAdapter()
    private val tagChildrenAdapter = MultiTypeAdapter()

    override fun createViewModel() = NavigatorChildViewModel()

    override fun init(savedInstanceState: Bundle?) {
        initView()
        initObserve()
    }

    private fun initView() {
        viewBinding.apply {
            with(tagList) {
                tagAdapter.register(NavigatorChildTagItemBinder(this@NavigatorChildFragment::onTagClick))
                adapter = tagAdapter
                layoutManager = LinearLayoutManager(context)
            }
            with(tagChildrenList) {
                tagChildrenAdapter.register(NavigatorChildTagChildrenItemBinder())
                adapter = tagChildrenAdapter
                layoutManager = LinearLayoutManager(context)
            }
        }
    }

    private fun initObserve() {
        viewModel.apply {
            navigationTagListLiveData.observe(viewLifecycleOwner) {
                dispatchToAdapter(it, tagAdapter)
                dispatchToAdapter(it, tagChildrenAdapter)
            }
        }
    }

    private fun dispatchToAdapter(
        result: Pair<List<Any>, DiffUtil.DiffResult>,
        adapter: MultiTypeAdapter
    ) {
        adapter.items = result.first
        result.second.dispatchUpdatesTo(adapter)
    }

    private fun onTagClick(action: Pair<Int, Navigation>) {
        val index = viewModel.navigationTagListLiveData.value?.first?.indexOfFirst {
            it is Navigation && it.name == action.second.name
        } ?: return
        viewBinding.tagChildrenList.smoothSnapToPosition(index)
        viewModel.onTagSelectedChange(index)

        //TODO ugly code
        viewModel.getCurrentList().forEachIndexed { index, any ->
            if (any is Navigation && any.isSelected) {
                any.isSelected = false
                tagAdapter.notifyItemChanged(index, TagSelectedChange)
            }
        }
        (viewModel.getCurrentList().get(index) as Navigation).isSelected = true
        tagAdapter.notifyItemChanged(index, TagSelectedChange)
    }
}