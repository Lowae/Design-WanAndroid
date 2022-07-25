package com.lowe.wanandroid.ui.group.child

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.lowe.common.base.BaseViewModel
import com.lowe.common.utils.tryOffer
import com.lowe.wanandroid.ui.group.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class GroupChildViewModel @Inject constructor(private val repository: GroupRepository) :
    BaseViewModel() {

    private val _fetchGroupArticles = Channel<Int>(Channel.CONFLATED)

    /**
     * 公众号文章Flow
     */
    val groupArticlesFlow = _fetchGroupArticles.receiveAsFlow().flatMapLatest {
        repository.getAuthorArticles(it)
    }.cachedIn(viewModelScope)

    fun fetch(id: Int) {
        _fetchGroupArticles.tryOffer(id)
    }
}