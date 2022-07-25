package com.lowe.common.services

import com.lowe.common.base.http.adapter.NetworkResponse
import com.lowe.common.services.model.Article
import com.lowe.common.services.model.Classify
import com.lowe.common.services.model.PageResponse
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