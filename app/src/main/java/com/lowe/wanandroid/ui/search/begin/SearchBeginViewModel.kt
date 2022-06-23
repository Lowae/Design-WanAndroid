package com.lowe.wanandroid.ui.search.begin

import androidx.datastore.preferences.core.edit
import androidx.lifecycle.MutableLiveData
import com.lowe.wanandroid.base.DataStoreManager
import com.lowe.wanandroid.base.SearchHistoryPreference
import com.lowe.wanandroid.di.ApplicationScope
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
    @ApplicationScope private val applicationScope: CoroutineScope
) :
    BaseViewModel() {

    val searchHotKeyLiveData = MutableLiveData<List<HotKeyBean>>()
    val historyLiveData = MutableLiveData<List<SearchState>>()

    private val historyLruCache = LimitedLruQueue<SearchState>(20)

    override fun start() {
        super.start()
        getHotKeys()
    }

    override fun onCleared() {
        super.onCleared()
        applicationScope.launch {
            DataStoreManager.dataStore.edit {
                it[SearchHistoryPreference.searchHistoryPreferences] =
                    historyLruCache.map { it.keywords }.toSet()
            }
        }
    }

    private fun getHotKeys() {
        launch({
            searchHotKeyLiveData.value = repository.getHotKeyList().success()?.data ?: emptyList()
        })
    }

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