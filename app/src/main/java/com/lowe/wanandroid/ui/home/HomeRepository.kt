package com.lowe.wanandroid.ui.home

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.lowe.wanandroid.base.IntKeyPagingSource
import com.lowe.wanandroid.base.http.adapter.getOrElse
import com.lowe.wanandroid.base.http.adapter.getOrNull
import com.lowe.wanandroid.services.BaseService
import com.lowe.wanandroid.services.HomeService
import com.lowe.wanandroid.services.model.Banners
import com.lowe.wanandroid.ui.BaseViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

/**
 * 首页Tab Repository
 */
class HomeRepository @Inject constructor(private val service: HomeService) {

    private suspend fun getBanner() = service.getBanner()

    private suspend fun getArticleTopList() = service.getArticleTopList()

    fun getArticlePageList(pageSize: Int) =
        Pager(
            PagingConfig(
                pageSize = pageSize,
                initialLoadSize = pageSize,
                enablePlaceholders = false
            )
        ) {
            IntKeyPagingSource(
                BaseService.DEFAULT_PAGE_START_NO,
                service = service
            ) { service, page, size ->
                // 根据Page来区分是否需要拉取Banner和置顶文章
                if (page == BaseService.DEFAULT_PAGE_START_NO) {
                    // 使用async并行请求
                    val (articlesDeferred, topsDeferred, bannersDeferred) =
                        supervisorScope {
                            Triple(
                                async { service.getArticlePageList(page, size) },
                                async { getArticleTopList() },
                                async { getBanner() })
                        }
                    val articles = articlesDeferred.await().getOrNull()?.datas ?: emptyList()
                    val tops = topsDeferred.await().getOrElse { emptyList() }
                    val banners = bannersDeferred.await().getOrElse { emptyList() }
                    with(ArrayList<Any>(1 + tops.size + articles.size)) {
                        add(Banners(banners))
                        addAll(tops)
                        addAll(articles)
                        this
                    }
                } else {
                    // page不为0则只是加载更多即可
                    service.getArticlePageList(page, size).getOrNull()?.datas ?: emptyList()
                }
            }
        }.flow

    fun getSquarePageList(pageSize: Int) =
        Pager(
            PagingConfig(
                pageSize = pageSize,
                initialLoadSize = pageSize,
                enablePlaceholders = false
            )
        ) {
            IntKeyPagingSource(
                BaseService.DEFAULT_PAGE_START_NO,
                service = service
            ) { service, page, size ->
                service.getSquarePageList(page, size).getOrNull()?.datas ?: emptyList()
            }
        }.flow

    fun getAnswerPageList() =
        Pager(
            PagingConfig(
                pageSize = BaseViewModel.DEFAULT_PAGE_SIZE,
                initialLoadSize = BaseViewModel.DEFAULT_PAGE_SIZE,
                enablePlaceholders = false
            )
        ) {
            IntKeyPagingSource(
                BaseService.DEFAULT_PAGE_START_NO_1,
                service = service
            ) { service, page, _ ->
                service.getAnswerPageList(page).getOrNull()?.datas ?: emptyList()
            }
        }.flow
}