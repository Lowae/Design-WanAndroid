package com.lowe.wanandroid.ui.search

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.lowe.wanandroid.services.model.Article
import com.lowe.wanandroid.ui.BaseViewModel
import com.lowe.wanandroid.utils.tryOffer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: SearchRepository) :
    BaseViewModel() {

    private val _shortcutSearch = Channel<String>(Channel.CONFLATED)
    val shortcutSearch = _shortcutSearch.receiveAsFlow()

    /**
     * 搜索状态StateFlow
     */
    val searchState: StateFlow<SearchState>

    /**
     * Pager Flow
     */
    val pagingDataFlow: Flow<PagingData<Article>>

    /**
     * 搜索触发
     */
    val searchAccept: (SearchAction) -> Unit

    init {
        val actionSharedFlow = MutableSharedFlow<SearchAction>(1)
        val searches = actionSharedFlow
            .filterIsInstance<SearchAction.Search>()
            .distinctUntilChanged()

        /**
         * 将搜索行为转换为Pager流
         */
        pagingDataFlow = searches
            .flatMapLatest { repository.search(it.query) }
            .cachedIn(viewModelScope)

        /**
         * 搜索状态流
         */
        searchState = searches
            .map { SearchState(it.query) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                initialValue = SearchState()
            )

        searchAccept = {
            /**
             * 对搜索流actionSharedFlow emit搜索动作
             */
            viewModelScope.launch { actionSharedFlow.emit(it) }
        }
    }

    fun shortcutSearch(keywords: String) {
        _shortcutSearch.tryOffer(keywords)
    }


    /**
     * 所以的搜索行为（热词、历史记录、手动查询）最终都会走到此处
     */
    fun search(keywords: String) {
        searchAccept(SearchAction.Search(keywords))
    }
}

/**
 * 目前暂时只有纯粹的搜索行为
 */
sealed class SearchAction {
    data class Search(val query: String) : SearchAction()
}

data class SearchState(
    val keywords: String = ""
)