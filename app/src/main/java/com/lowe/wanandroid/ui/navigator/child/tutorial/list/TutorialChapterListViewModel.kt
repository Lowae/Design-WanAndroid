package com.lowe.wanandroid.ui.navigator.child.tutorial.list

import androidx.lifecycle.MutableLiveData
import com.lowe.wanandroid.base.http.adapter.getOrNull
import com.lowe.wanandroid.services.model.Article
import com.lowe.wanandroid.ui.BaseViewModel
import com.lowe.wanandroid.ui.launch
import com.lowe.wanandroid.ui.navigator.NavigatorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TutorialChapterListViewModel @Inject constructor(private val repository: NavigatorRepository) :
    BaseViewModel() {

    val chaptersLiveData = MutableLiveData<List<Article>>()

    fun fetchChapterList(tutorialId: Int) {
        launch({
            chaptersLiveData.value =
                repository.getTutorialChapterList(tutorialId).getOrNull()?.datas ?: emptyList()
        })

    }
}