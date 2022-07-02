package com.lowe.wanandroid.ui.navigator.child.tutorial

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import com.lowe.wanandroid.services.model.success
import com.lowe.wanandroid.ui.ArticleDiffCalculator
import com.lowe.wanandroid.ui.BaseViewModel
import com.lowe.wanandroid.ui.launch
import com.lowe.wanandroid.ui.navigator.NavigatorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TutorialChildViewModel @Inject constructor(private val repository: NavigatorRepository) :
    BaseViewModel() {

    private val _tutorialListLiveData = MutableLiveData<Pair<List<Any>, DiffUtil.DiffResult>>()
    val tutorialListLiveData: LiveData<Pair<List<Any>, DiffUtil.DiffResult>> = _tutorialListLiveData

    override fun init() {
        fetchTutorialList()
    }

    fun fetchTutorialList() {
        launch({
            val tutorials = repository.getTutorialList().success()?.data ?: emptyList()
            _tutorialListLiveData.value =
                getDiffResultPair(_tutorialListLiveData.value?.first ?: emptyList(), tutorials)
        })
    }

    private fun getDiffResultPair(oldList: List<Any>, newList: List<Any>) =
        newList to DiffUtil.calculateDiff(
            ArticleDiffCalculator.getCommonDiffCallback(
                oldList,
                newList
            )
        )
}