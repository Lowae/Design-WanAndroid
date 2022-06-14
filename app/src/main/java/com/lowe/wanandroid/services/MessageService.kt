package com.lowe.wanandroid.services

import com.lowe.wanandroid.services.model.MsgBean
import retrofit2.http.GET
import retrofit2.http.Path

interface MessageService : BaseService {

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

}