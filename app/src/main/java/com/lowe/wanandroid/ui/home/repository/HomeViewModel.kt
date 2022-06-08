package com.lowe.wanandroid.ui.home.repository

import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import com.lowe.wanandroid.services.model.Banners
import com.lowe.wanandroid.services.success
import com.lowe.wanandroid.ui.ArticleDiffCalculator
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
     * 文章列表请求
     */
    fun fetchArticleList() {
        isLoading = true
        launch({
            currentList = if (page == 0) {
                // Banner
                val banners = async { HomeRepository.getBanner() }
                // 置顶文字
                val pageListDeferred =
                    async { HomeRepository.getArticlePageList(page, DEFAULT_PAGE_SIZE) }
                // 文章列表
                val topListDeferred = async { HomeRepository.getArticleTopList() }
                with(mutableListOf<Any>()) {
                    add(Banners(banners.await().success()?.data ?: emptyList()))
                    addAll((topListDeferred.await().success()?.data ?: emptyList()))
                    addAll(pageListDeferred.await().success()?.data?.datas ?: emptyList())

                    articleListLiveData.value = getDiffResultPair(currentList, this)
                    this
                }
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
    ): Pair<List<Any>, DiffUtil.DiffResult> = newList to DiffUtil.calculateDiff(
        ArticleDiffCalculator.getCommonArticleDiffCalculator(
            oldList,
            newList
        )
    )

    override fun start() {

    }
}