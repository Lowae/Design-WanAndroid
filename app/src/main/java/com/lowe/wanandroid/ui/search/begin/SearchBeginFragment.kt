package com.lowe.wanandroid.ui.search.begin

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayoutManager
import com.lowe.multitype.MultiTypeAdapter
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.FragmentSearchBeginBinding
import com.lowe.wanandroid.services.model.HotKeyBean
import com.lowe.wanandroid.ui.BaseFragment
import com.lowe.wanandroid.ui.search.SearchState
import com.lowe.wanandroid.ui.search.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchBeginFragment :
    BaseFragment<SearchBeginViewModel, FragmentSearchBeginBinding>(R.layout.fragment_search_begin) {

    private val hotKeyAdapter = MultiTypeAdapter().apply {
        register(SearchHotKeyTagItemBinder(this@SearchBeginFragment::onHotKeyClick))
    }
    private val historyAdapter = MultiTypeAdapter().apply {
        register(
            SearchHistoryItemBinder(
                this@SearchBeginFragment::onSearchHistoryClick,
                this@SearchBeginFragment::onHistoryDeleteClick
            )
        )
    }

    private val searchActivityViewModel by activityViewModels<SearchViewModel>()

    override val viewModel: SearchBeginViewModel by viewModels()

    override fun onViewCreated(savedInstanceState: Bundle?) {
        initView()
        initEvent()
    }

    private fun initView() {
        viewDataBinding.apply {
            with(hotkeyList) {
                adapter = hotKeyAdapter
                layoutManager = FlexboxLayoutManager(context)
                setHasFixedSize(true)
            }
            with(historyList) {
                adapter = historyAdapter
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, true)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initEvent() {
        viewModel.searchHotKeyLiveData.observe(this) {
            hotKeyAdapter.items = it
            hotKeyAdapter.notifyItemRangeInserted(0, hotKeyAdapter.itemCount)
        }
        viewModel.historyLiveData.observe(this) {
            historyAdapter.items = it
            historyAdapter.notifyDataSetChanged()
        }
        lifecycleScope.launch {
            searchActivityViewModel.searchState
                .filter { it.keywords.isNotBlank() }
                .collect(viewModel::historyPut)
        }
        lifecycleScope.launch {
            viewModel.searchHistoryFlow().collectLatest { save ->
                historyAdapter.items = save
                historyAdapter.notifyDataSetChanged()
                viewModel.initHistoryCache(save)
            }
        }
    }

    private fun onHistoryDeleteClick(position: Int, searchBean: SearchState) {
        viewModel.historyRemove(searchBean)
    }

    private fun onSearchHistoryClick(position: Int, searchBean: SearchState) {
        viewModel.historyPut(searchBean)
        shortcutSearch(searchBean.keywords)
    }

    private fun onHotKeyClick(hotKeyBean: HotKeyBean) {
        viewModel.historyPut(SearchState(hotKeyBean.name))
        shortcutSearch(hotKeyBean.name)
    }

    private fun shortcutSearch(keywords: String) {
        searchActivityViewModel.shortcutSearch(keywords)
    }

}