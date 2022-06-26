package com.lowe.wanandroid.services

import com.lowe.wanandroid.services.model.CollectBean
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CollectService : BaseService {

    @GET("lg/collect/list/{page}/json")
    suspend fun getCollectList(@Path("page") page: Int): ApiResponse<PageResponse<CollectBean>>

    /** 收藏站内文章 */
    @POST("lg/collect/{id}/json")
    suspend fun collectArticle(@Path("id") id: Int): ApiResponse<Any?>

    /** 取消收藏站内文章 */
    @POST("lg/uncollect_originId/{id}/json")
    suspend fun unCollectArticle(@Path("id") id: Int): ApiResponse<Any?>

    suspend fun isCollectArticle(collect: Boolean, id: Int) =
        if (collect) collectArticle(id) else unCollectArticle(id)

}