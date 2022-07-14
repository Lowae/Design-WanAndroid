package com.lowe.wanandroid.ui.search.begin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.lowe.wanandroid.base.http.adapter.getOrElse
import com.lowe.wanandroid.di.ApplicationScope
import com.lowe.wanandroid.services.model.HotKeyBean
import com.lowe.wanandroid.ui.BaseViewModel
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
    @ApplicationScope private val applicationScope: CoroutineScope
) :
    BaseViewModel() {

    val searchHotKeyLiveData: LiveData<List<HotKeyBean>> = liveData {
        emit(
            repository.getHotKeyList().getOrElse { emptyList() }
        )
    }
    private val _historyLiveData = MutableLiveData<List<SearchState>>()
    val historyLiveData: LiveData<List<SearchState>> = _historyLiveData

    /**
     * 保存搜查记录的LRUCache
     */
    private val historyLruCache = LimitedLruQueue<SearchState>(20)

    override fun onCleared() {
        super.onCleared()
        /**
         * 更新搜索记录，在ViewModel生命周期结束后触发，所以需要applicationScope来开启协程
         */
        applicationScope.launch {
            repository.updateSearchHistory(historyLruCache.map { it.keywords }.toSet())
        }
    }

    fun searchHistoryFlow() = repository.searchHistoryCache()

    fun initHistoryCache(histories: List<SearchState>) {
        historyLruCache.addAll(histories)
    }

    fun historyPut(state: SearchState) {
        historyLruCache.add(state)
        _historyLiveData.value = historyLruCache.toList()
    }

    fun historyRemove(state: SearchState) {
        historyLruCache.remove(state)
        _historyLiveData.value = historyLruCache.toList()
    }

}