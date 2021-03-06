package com.lowe.wanandroid.services

import com.lowe.wanandroid.base.http.adapter.NetworkResponse
import com.lowe.wanandroid.services.model.Article
import com.lowe.wanandroid.services.model.Classify
import com.lowe.wanandroid.services.model.PageResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GroupService : BaseService {

    /**
     * 公众号作者列表
     */
    @GET("wxarticle/chapters/json")
    suspend fun getAuthorTitleList(): NetworkResponse<List<Classify>>

    /**
     * 对于id作者的文章
     */
    @GET("wxarticle/list/{id}/{page}/json")
    suspend fun getAuthorArticles(
        @Path("id") id: Int,
        @Path("page") page: Int,
        @Query("page_size") pageSize: Int
    ): NetworkResponse<PageResponse<Article>>

}