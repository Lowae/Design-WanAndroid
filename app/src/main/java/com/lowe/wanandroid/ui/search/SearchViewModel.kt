package com.lowe.wanandroid.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.lowe.wanandroid.services.model.Article
import com.lowe.wanandroid.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: SearchRepository) :
    BaseViewModel() {

    private val _shortcutSearchLiveData = MutableLiveData<String>()
    val shortcutSearchLiveData: LiveData<String> = _shortcutSearchLiveData

    val searchState: StateFlow<SearchState>

    val pagingDataFlow: Flow<PagingData<Article>>

    val searchAccept: (SearchAction) -> Unit

    init {
        val actionSharedFlow = MutableSharedFlow<SearchAction>(1)
        val searches = actionSharedFlow
            .filterIsInstance<SearchAction.Search>()
            .distinctUntilChanged()

        pagingDataFlow = searches
            .flatMapLatest { repository.search(it.query) }
            .cachedIn(viewModelScope)

        searchState = searches
            .map { SearchState(it.query) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                initialValue = SearchState()
            )

        searchAccept = {
            viewModelScope.launch { actionSharedFlow.emit(it) }
        }
    }

    fun shortcutSearch(keywords: String) {
        _shortcutSearchLiveData.value = keywords
    }


    fun search(keywords: String) {
        searchAccept(SearchAction.Search(keywords))
    }
}

sealed class SearchAction {
    data class Search(val query: String) : SearchAction()
}

data class SearchState(
    val keywords: String = ""
)