package com.lowe.common.services.impl

import com.lowe.common.base.http.RetrofitManager
import com.lowe.common.services.CoinService
import javax.inject.Inject

class CoinServiceImpl @Inject constructor() : CoinService {

    private val service by lazy { RetrofitManager.getService(CoinService::class.java) }

    override suspend fun getMyCoinList(page: Int) = service.getMyCoinList(page)

    override suspend fun getCoinRanking(page: Int) = service.getCoinRanking(page)
}