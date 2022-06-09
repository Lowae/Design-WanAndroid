package com.lowe.wanandroid.services

import com.lowe.wanandroid.services.model.Navigation
import retrofit2.http.GET

interface NavigatorService : BaseService {

    /** 获取导航数据 */
    @GET("navi/json")
    suspend fun getNavigationList(): ApiResponse<List<Navigation>>

}
