package com.lowe.wanandroid.ui.home.repository

import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import com.lowe.wanandroid.services.success
import com.lowe.wanandroid.ui.BaseViewModel
import com.lowe.wanandroid.ui.launch
import kotlinx.coroutines.async

class HomeViewModel : BaseViewModel() {

    companion object {
        private const val DEFAULT_PAGE_SIZE = 10
    }

    private var page = 0
    private var currentList = emptyList<Any>()

    val articleListLiveData = MutableLiveData<Pair<List<Any>, DiffUtil.DiffResult>>()
    var isLoading = false

    fun refreshArticleList() {
        page = 0
        fetchArticleList()
    }

    /**
     * 拉取轮播图
     */
    fun fetchHeadBanner() {
        launch({
            HomeRepository.getBanner().apply {

            }
        })
    }

    /**
     * 文章列表请求
     */
    fun fetchArticleList() {
        isLoading = true
        launch({
            currentList = if (page == 0) {
                val pageListDeferred =
                    async { HomeRepository.getArticlePageList(page, DEFAULT_PAGE_SIZE) }
                val topListDeferred = async { HomeRepository.getArticleTopList() }
                val newList = (topListDeferred.await().success()?.data
                    ?: emptyList()) + (pageListDeferred.await().success()?.data?.datas
                    ?: emptyList())
                articleListLiveData.value = getDiffResultPair(currentList, newList)
                newList
            } else {
                HomeRepository.getArticlePageList(page, DEFAULT_PAGE_SIZE).run {
                    val newList = currentList + data.datas
                    articleListLiveData.value = getDiffResultPair(currentList, newList)
                    newList
                }
            }
            page++
            isLoading = false
        })
    }

    private fun getDiffResultPair(
        oldList: List<Any>,
        newList: List<Any>
    ): Pair<List<Any>, DiffUtil.DiffResult> =
        newList to DiffUtil.calculateDiff(HomeArticleDiffCalculator(oldList, newList))

    override fun start() {

    }
}