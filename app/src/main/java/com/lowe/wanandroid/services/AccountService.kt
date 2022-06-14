package com.lowe.wanandroid.services

import com.lowe.wanandroid.services.model.User
import com.lowe.wanandroid.services.model.UserBaseInfo
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface AccountService : BaseService {

    /** 登录 */
    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): ApiResponse<User>

    @GET("user/lg/userinfo/json")
    suspend fun getUserInfo():ApiResponse<UserBaseInfo>
}