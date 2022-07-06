package com.lowe.wanandroid.ui.search

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.lowe.wanandroid.base.IntKeyPagingSource
import com.lowe.wanandroid.base.http.SearchHistoryPreference
import com.lowe.wanandroid.base.http.adapter.getOrNull
import com.lowe.wanandroid.services.SearchService
import com.lowe.wanandroid.ui.BaseViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val searchService: SearchService,
    private val dataStore: DataStore<Preferences>,
) {

    suspend fun getHotKeyList() = searchService.getSearchHotKey()

    fun search(keywords: String) =
        Pager(
            PagingConfig(
                pageSize = BaseViewModel.DEFAULT_PAGE_SIZE,
                initialLoadSize = BaseViewModel.DEFAULT_PAGE_SIZE,
                enablePlaceholders = false
            )
        ) {
            IntKeyPagingSource(service = searchService) { service, page, _ ->
                service.queryBySearchKey(page, keywords).getOrNull()?.datas ?: emptyList()
            }
        }.flow

    fun searchHistoryCache() = dataStore.data.map {
        (it[SearchHistoryPreference.searchHistoryPreferences]
            ?: emptySet()).map { SearchState(it) }
    }

    suspend fun updateSearchHistory(set: Set<String>) {
        dataStore.edit {
            it[SearchHistoryPreference.searchHistoryPreferences] = set
        }
    }

}