package com.lowe.wanandroid.services

import com.lowe.wanandroid.services.model.Article
import com.lowe.wanandroid.services.model.Classify
import com.lowe.wanandroid.services.model.Navigation
import com.lowe.wanandroid.services.model.Series
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NavigatorService : BaseService {

    /** 获取导航数据 */
    @GET("navi/json")
    suspend fun getNavigationList(): ApiResponse<List<Navigation>>

    /** 获取体系数据 */
    @GET("tree/json")
    suspend fun getTreeList(): ApiResponse<List<Series>>

    /** 教程列表 */
    @GET("chapter/547/sublist/json")
    suspend fun getTutorialList(): ApiResponse<List<Classify>>

    @GET("article/list/0/json")
    suspend fun getTutorialChapterList(
        @Query("cid") id: Int,
        @Query("order_type") orderType: Int = 1
    ): ApiResponse<PageResponse<Article>>

    @GET("article/list/{page}/json")
    suspend fun getSeriesDetailList(
        @Path("page") page: Int,
        @Query("cid") id: Int,
        @Query("page_size") size: Int
    ): ApiResponse<PageResponse<Article>>
}
