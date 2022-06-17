package com.lowe.wanandroid.services

import com.lowe.wanandroid.services.model.*
import retrofit2.http.*

interface ProfileService : BaseService {


    /** 登录 */
    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): ApiResponse<User>

    @GET("user/lg/userinfo/json")
    suspend fun getUserInfo():ApiResponse<UserBaseInfo>

    /**
     * 已读消息列表
     */
    @GET("message/lg/readed_list/{page}/json")
    suspend fun getReadiedMessageList(@Path("page") page: Int):ApiResponse<PageResponse<MsgBean>>

    /**
     * 未读消息列表
     */
    @GET("message/lg/unread_list//{page}/json")
    suspend fun getUnReadMessageList(@Path("page") page: Int):ApiResponse<PageResponse<MsgBean>>

    @GET("user/lg/private_articles/{page}/json")
    suspend fun getMyShareList(@Path("page") page: Int): ApiResponse<ShareBean>

    @GET("user/{userId}/share_articles/{page}/json")
    suspend fun getUserShareList(
        @Path("userId") userId: Int,
        @Path("page") page: Int
    ): ApiResponse<ShareBean>

    @GET("lg/collect/list/{page}/json")
    suspend fun getCollectList(@Path("page") page: Int): ApiResponse<PageResponse<CollectBean>>

    @GET("tools/list/json")
    suspend fun getToolList(): ApiResponse<List<ToolBean>>
}