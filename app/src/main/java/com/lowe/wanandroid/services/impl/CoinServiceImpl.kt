package com.lowe.wanandroid.services.impl

import com.lowe.wanandroid.base.http.RetrofitManager
import com.lowe.wanandroid.services.CoinService
import javax.inject.Inject

class CoinServiceImpl @Inject constructor() : CoinService {

    private val service by lazy { RetrofitManager.getService(CoinService::class.java) }

    override suspend fun getMyCoinList(page: Int) = service.getMyCoinList(page)

    override suspend fun getCoinRanking(page: Int) = service.getCoinRanking(page)
}