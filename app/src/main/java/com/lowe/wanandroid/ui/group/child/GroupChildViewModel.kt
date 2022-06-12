package com.lowe.wanandroid.ui.group.child

import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import com.lowe.wanandroid.services.success
import com.lowe.wanandroid.ui.ArticleDiffCalculator
import com.lowe.wanandroid.ui.BaseViewModel
import com.lowe.wanandroid.ui.group.repository.GroupRepository
import com.lowe.wanandroid.ui.launch

class GroupChildViewModel : BaseViewModel() {

    val groupChildArticlesLiveData = MutableLiveData<Pair<List<Any>, DiffUtil.DiffResult>>()
    var isLoading = false
    private var page = 1

    override fun start() {
    }

    fun fetchArticles(id: Int, isRefresh: Boolean = false) {
        isLoading = true
        page = if (isRefresh) 1 else page
        launch({
            val articles = GroupRepository.getAuthorArticles(id, page, DEFAULT_PAGE_SIZE)
                .success()?.data?.datas ?: emptyList()
            val oldList = groupChildArticlesLiveData.value?.first ?: emptyList()
            groupChildArticlesLiveData.value = getDiffResultPair(oldList, if (isRefresh) articles else oldList + articles)
            page++
            isLoading = false
        })
    }

    private fun getDiffResultPair(oldList: List<Any>, newList: List<Any>) =
        newList to DiffUtil.calculateDiff(
            ArticleDiffCalculator.getCommonArticleDiffCalculator(
                oldList,
                newList
            )
        )

}