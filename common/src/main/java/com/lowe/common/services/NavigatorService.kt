package com.lowe.common.services

import com.lowe.common.base.http.adapter.NetworkResponse
import com.lowe.common.services.model.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NavigatorService : BaseService {

    /**
     * 导航数据
     */
    @GET("navi/json")
    suspend fun getNavigationList(): NetworkResponse<List<Navigation>>

    /**
     * 体系数据
     */
    @GET("tree/json")
    suspend fun getTreeList(): NetworkResponse<List<Series>>

    /**
     * 教程列表
     */
    @GET("chapter/547/sublist/json")
    suspend fun getTutorialList(): NetworkResponse<List<Classify>>

    /**
     * 对应教程的章节列表
     */
    @GET("article/list/0/json")
    suspend fun getTutorialChapterList(
        @Query("cid") id: Int,
        @Query("order_type") orderType: Int = 1
    ): NetworkResponse<PageResponse<Article>>

    /**
     * 系列对应Tag的文章列表
     */
    @GET("article/list/{page}/json")
    suspend fun getSeriesDetailList(
        @Path("page") page: Int,
        @Query("cid") id: Int,
        @Query("page_size") size: Int
    ): NetworkResponse<PageResponse<Article>>
}
