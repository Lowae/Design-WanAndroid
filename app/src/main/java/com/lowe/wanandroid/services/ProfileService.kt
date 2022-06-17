package com.lowe.wanandroid.services

import com.lowe.wanandroid.services.model.CollectBean
import com.lowe.wanandroid.services.model.ShareBean
import com.lowe.wanandroid.services.model.ToolBean
import retrofit2.http.GET
import retrofit2.http.Path

interface ProfileService : BaseService {

    @GET("user/lg/private_articles/{page}/json")
    suspend fun getMyShareList(@Path("page") page: Int): ApiResponse<ShareBean>

    @GET("user/{userId}/share_articles/{page}/json")
    suspend fun getUserShareList(
        @Path("userId") userId: Int,
        @Path("page") page: Int
    ): ApiResponse<ShareBean>

    @GET("lg/collect/list/{page}/json")
    suspend fun getCollectList(@Path("page") page: Int): ApiResponse<PageResponse<CollectBean>>

    @GET("https://wanandroid.com/tools/list/json")
    suspend fun getToolList(): ApiResponse<List<ToolBean>>
}