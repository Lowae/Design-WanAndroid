package com.lowe.wanandroid.services

import com.lowe.wanandroid.services.model.Article
import com.lowe.wanandroid.services.model.Banner
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeService : BaseService {

    /** 获取首页banner数据 */
    @GET("banner/json")
    suspend fun getBanner(): ApiResponse<List<Banner>>

    /** 获取首页文章数据 */
    @GET("article/list/{pageNo}/json")
    suspend fun getArticlePageList(
        @Path("pageNo") pageNo: Int,
        @Query("page_size") pageSize: Int
    ): ApiResponse<PageResponse<Article>>

    /** 获取置顶文章集合数据 */
    @GET("article/top/json")
    suspend fun getArticleTopList(): ApiResponse<List<Article>>
}
