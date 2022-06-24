package com.lowe.wanandroid.services

import com.lowe.wanandroid.services.model.User
import com.lowe.wanandroid.services.model.UserBaseInfo
import retrofit2.http.*

interface CommonService : BaseService {

    /** 登录 */
    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): ApiResponse<User>

    @GET("user/lg/userinfo/json")
    suspend fun getUserInfo():ApiResponse<UserBaseInfo>

    /** 收藏站内文章 */
    @POST("lg/collect/{id}/json")
    suspend fun collectArticle(@Path("id") id: Int): ApiResponse<Any?>

    /** 取消收藏站内文章 */
    @POST("lg/uncollect_originId/{id}/json")
    suspend fun unCollectArticle(@Path("id") id: Int): ApiResponse<Any?>

    suspend fun isCollectArticle(collect: Boolean, id: Int) =
        if (collect) collectArticle(id) else unCollectArticle(id)

}