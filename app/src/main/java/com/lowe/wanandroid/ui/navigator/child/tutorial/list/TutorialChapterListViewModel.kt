package com.lowe.wanandroid.ui.navigator.child.tutorial.list

import androidx.lifecycle.viewModelScope
import com.lowe.common.base.http.adapter.getOrNull
import com.lowe.common.services.model.Article
import com.lowe.common.base.BaseViewModel
import com.lowe.wanandroid.ui.navigator.NavigatorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TutorialChapterListViewModel @Inject constructor(private val repository: NavigatorRepository) :
    BaseViewModel() {

    private val _chaptersSharedFlow = MutableSharedFlow<List<Article>>(replay = 1)
    val chaptersFlow: Flow<List<Article>> = _chaptersSharedFlow

    fun fetchChapterList(tutorialId: Int) {
        viewModelScope.launch {
            _chaptersSharedFlow.emit(
                repository.getTutorialChapterList(tutorialId).getOrNull()?.datas ?: emptyList()
            )
        }
    }
}