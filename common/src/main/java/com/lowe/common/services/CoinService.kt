package com.lowe.common.services

import com.lowe.common.base.http.adapter.NetworkResponse
import com.lowe.common.services.model.CoinHistory
import com.lowe.common.services.model.CoinInfo
import com.lowe.common.services.model.PageResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinService : BaseService {

    @GET("/lg/coin/list/{page}/json")
    suspend fun getMyCoinList(@Path("page") page: Int): NetworkResponse<PageResponse<CoinHistory>>

    @GET("coin/rank/{page}/json")
    suspend fun getCoinRanking(@Path("page") page: Int): NetworkResponse<PageResponse<CoinInfo>>

}