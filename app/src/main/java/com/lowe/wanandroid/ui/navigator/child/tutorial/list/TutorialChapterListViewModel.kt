package com.lowe.wanandroid.ui.navigator.child.tutorial.list

import androidx.lifecycle.MutableLiveData
import com.lowe.wanandroid.services.model.Article
import com.lowe.wanandroid.services.success
import com.lowe.wanandroid.ui.BaseViewModel
import com.lowe.wanandroid.ui.launch
import com.lowe.wanandroid.ui.navigator.NavigatorRepository

class TutorialChapterListViewModel : BaseViewModel() {

    val chaptersLiveData = MutableLiveData<List<Article>>()

    override fun start() {

    }

    fun fetchChapterList(tutorialId: Int) {
        launch({
            chaptersLiveData.value =
                NavigatorRepository.getTutorialChapterList(tutorialId).success()?.data?.datas
                    ?: emptyList()
        })

    }
}