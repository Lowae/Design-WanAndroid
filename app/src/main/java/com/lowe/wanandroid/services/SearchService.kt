package com.lowe.wanandroid.services

import com.lowe.wanandroid.services.model.ApiResponse
import com.lowe.wanandroid.services.model.Article
import com.lowe.wanandroid.services.model.HotKeyBean
import com.lowe.wanandroid.services.model.PageResponse
import retrofit2.http.*

interface SearchService : BaseService {

    @GET("hotkey/json")
    suspend fun getSearchHotKey(): ApiResponse<List<HotKeyBean>>

    @POST("article/query/{page}/json")
    @FormUrlEncoded
    suspend fun queryBySearchKey(
        @Path("page") page: Int,
        @Field("k") key: String
    ): ApiResponse<PageResponse<Article>>
}