package com.lowe.wanandroid.services

import com.lowe.wanandroid.base.http.adapter.NetworkResponse
import com.lowe.wanandroid.services.model.MsgBean
import com.lowe.wanandroid.services.model.PageResponse
import com.lowe.wanandroid.services.model.ShareBean
import com.lowe.wanandroid.services.model.ToolBean
import retrofit2.http.GET
import retrofit2.http.Path

interface ProfileService : BaseService {

    /**
     * 已读消息列表
     */
    @GET("message/lg/readed_list/{page}/json")
    suspend fun getReadiedMessageList(@Path("page") page: Int): NetworkResponse<PageResponse<MsgBean>>

    /**
     * 未读消息列表
     */
    @GET("message/lg/unread_list//{page}/json")
    suspend fun getUnReadMessageList(@Path("page") page: Int): NetworkResponse<PageResponse<MsgBean>>

    /**
     * 我的分享文章列表
     */
    @GET("user/lg/private_articles/{page}/json")
    suspend fun getMyShareList(@Path("page") page: Int): NetworkResponse<ShareBean>

    /**
     * 对应用户的分享文章列表
     */
    @GET("user/{userId}/share_articles/{page}/json")
    suspend fun getUserShareList(
        @Path("userId") userId: String,
        @Path("page") page: Int
    ): NetworkResponse<ShareBean>

    /**
     * 工具列表
     */
    @GET("tools/list/json")
    suspend fun getToolList(): NetworkResponse<List<ToolBean>>

    @GET("message/lg/count_unread/json")
    suspend fun getUnreadMessageCount(): NetworkResponse<Int>
}