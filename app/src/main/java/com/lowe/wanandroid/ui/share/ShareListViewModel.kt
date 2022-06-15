package com.lowe.wanandroid.ui.share

import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import com.lowe.wanandroid.services.model.ShareBean
import com.lowe.wanandroid.services.success
import com.lowe.wanandroid.ui.ArticleDiffCalculator
import com.lowe.wanandroid.ui.BaseViewModel
import com.lowe.wanandroid.ui.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShareListViewModel @Inject constructor(private val repository: ShareListRepository) :
    BaseViewModel() {

    val shareArticlesLiveData = MutableLiveData<Pair<ShareBean, DiffUtil.DiffResult>>()

    private var page = 1
    var isLoading = false

    override fun start() {
        super.start()
        fetchShareList()
    }

    fun fetchShareList() {
        isLoading = true
        launch({
            val shareBean = repository.getShareList(page).success()?.data
            shareBean?.also {
                val oldList =
                    shareArticlesLiveData.value?.first?.shareArticles?.datas ?: emptyList()
                shareArticlesLiveData.value = it to getDiffResult(oldList, it.shareArticles.datas)
                page++
                isLoading = false
            }
        })
    }

    private fun getDiffResult(oldList: List<Any>, newList: List<Any>) = DiffUtil.calculateDiff(
        ArticleDiffCalculator.getCommonArticleDiffCalculator(
            oldList,
            newList
        )
    )
}