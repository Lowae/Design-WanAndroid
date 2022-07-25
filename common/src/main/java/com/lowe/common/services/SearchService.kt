package com.lowe.common.services

import com.lowe.common.base.http.adapter.NetworkResponse
import com.lowe.common.services.model.Article
import com.lowe.common.services.model.HotKeyBean
import com.lowe.common.services.model.PageResponse
import retrofit2.http.*

interface SearchService : BaseService {

    /**
     * 热搜词
     */
    @GET("hotkey/json")
    suspend fun getSearchHotKey(): NetworkResponse<List<HotKeyBean>>

    /**
     * 搜索
     */
    @POST("article/query/{page}/json")
    @FormUrlEncoded
    suspend fun queryBySearchKey(
        @Path("page") page: Int,
        @Field("k") key: String
    ): NetworkResponse<PageResponse<Article>>
}