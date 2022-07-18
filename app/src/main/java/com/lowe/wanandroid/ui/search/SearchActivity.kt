package com.lowe.wanandroid.ui.search

import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.ActivitySearchBinding
import com.lowe.wanandroid.ui.ActivityDataBindingDelegate
import com.lowe.wanandroid.ui.BaseActivity
import com.lowe.wanandroid.ui.search.begin.SearchBeginFragment
import com.lowe.wanandroid.ui.search.result.SearchListFragment
import com.lowe.wanandroid.utils.hideSoftKeyboard
import com.lowe.wanandroid.utils.launchRepeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchActivity : BaseActivity<SearchViewModel, ActivitySearchBinding>() {

    private var searchBeginFragment = SearchBeginFragment()
    private var searchListFragment = SearchListFragment()

    override val viewDataBinding: ActivitySearchBinding by ActivityDataBindingDelegate(R.layout.activity_search)

    override val viewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(
                    R.id.searchFragmentContainer,
                    searchBeginFragment,
                    searchBeginFragment.javaClass.simpleName
                )
                .commit()
        } else {
            searchBeginFragment =
                supportFragmentManager.findFragmentByTag(SearchBeginFragment::class.java.simpleName) as? SearchBeginFragment
                    ?: searchBeginFragment

            searchListFragment =
                supportFragmentManager.findFragmentByTag(SearchListFragment::class.java.simpleName) as? SearchListFragment
                    ?: searchListFragment
        }
        initView()
        initEvent()
    }

    private fun initView() {
        viewDataBinding.apply {
            with(backIcon) {
                setOnClickListener {
                    if (supportFragmentManager.backStackEntryCount != 0) {
                        supportFragmentManager.popBackStack()
                    } else {
                        finish()
                    }
                }
            }
            with(searchIcon) {
                setOnClickListener {
                    search(searchEdit.text?.trim().toString())
                }
            }
            with(searchEdit) {
                setOnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_GO) {
                        search(searchEdit.text?.trim().toString())
                        true
                    } else {
                        false
                    }
                }
                setOnKeyListener { _, keyCode, event ->
                    if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                        search(searchEdit.text?.trim().toString())
                        true
                    } else {
                        false
                    }
                }
                requestFocus()
            }
        }
    }

    private fun initEvent() {
        launchRepeatOnStarted {
            launch {
                viewModel.shortcutSearch.collect(this@SearchActivity::search)
            }

            launch {
                viewModel.searchState
                    .map { it.keywords }
                    .distinctUntilChanged()
                    .collect(this@SearchActivity::setSearchText)
            }
        }
    }

    private fun search(keywords: String) {
        viewDataBinding.searchEdit.hideSoftKeyboard()
        if (keywords.isBlank()) return
        if (searchListFragment.isAdded.not()) {
            supportFragmentManager.beginTransaction()
                .hide(searchBeginFragment)
                .add(
                    R.id.searchFragmentContainer,
                    searchListFragment,
                    searchListFragment.javaClass.simpleName
                )
                .addToBackStack(null).commit()
        }
        viewModel.search(keywords)
    }

    private fun setSearchText(text: String) {
        viewDataBinding.searchEdit.setText(text)
        viewDataBinding.searchEdit.setSelection(text.length)
    }

}