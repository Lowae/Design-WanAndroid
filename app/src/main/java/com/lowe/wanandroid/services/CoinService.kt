package com.lowe.wanandroid.services

import com.lowe.wanandroid.base.http.adapter.NetworkResponse
import com.lowe.wanandroid.services.model.CoinHistory
import com.lowe.wanandroid.services.model.CoinInfo
import com.lowe.wanandroid.services.model.PageResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinService : BaseService {

    @GET("/lg/coin/list/{page}/json")
    suspend fun getMyCoinList(@Path("page") page: Int): NetworkResponse<PageResponse<CoinHistory>>

    @GET("coin/rank/{page}/json")
    suspend fun getCoinRanking(@Path("page") page: Int): NetworkResponse<PageResponse<CoinInfo>>

}