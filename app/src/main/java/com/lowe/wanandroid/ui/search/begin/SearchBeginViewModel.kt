package com.lowe.wanandroid.ui.search.begin

import androidx.lifecycle.MutableLiveData
import com.lowe.wanandroid.di.DefaultApplicationScope
import com.lowe.wanandroid.services.model.HotKeyBean
import com.lowe.wanandroid.services.success
import com.lowe.wanandroid.ui.BaseViewModel
import com.lowe.wanandroid.ui.launch
import com.lowe.wanandroid.ui.search.SearchRepository
import com.lowe.wanandroid.ui.search.SearchState
import com.lowe.wanandroid.widgets.LimitedLruQueue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchBeginViewModel @Inject constructor(
    private val repository: SearchRepository,
    @DefaultApplicationScope private val applicationScope: CoroutineScope
) :
    BaseViewModel() {

    val searchHotKeyLiveData = MutableLiveData<List<HotKeyBean>>()
    val historyLiveData = MutableLiveData<List<SearchState>>()

    private val historyLruCache = LimitedLruQueue<SearchState>(20)

    override fun init() {
        super.init()
        getHotKeys()
    }

    override fun onCleared() {
        super.onCleared()
        applicationScope.launch {
            repository.updateSearchHistory(historyLruCache.map { it.keywords }.toSet())
        }
    }

    private fun getHotKeys() {
        launch({
            searchHotKeyLiveData.value = repository.getHotKeyList().success()?.data ?: emptyList()
        })
    }

    fun searchHistoryFlow() = repository.searchHistoryCache()

    fun initHistoryCache(histories: List<SearchState>) {
        historyLruCache.addAll(histories)
    }

    fun historyPut(state: SearchState) {
        historyLruCache.add(state)
        historyLiveData.value = historyLruCache.toList()
    }

    fun historyRemove(state: SearchState) {
        historyLruCache.remove(state)
        historyLiveData.value = historyLruCache.toList()
    }

}