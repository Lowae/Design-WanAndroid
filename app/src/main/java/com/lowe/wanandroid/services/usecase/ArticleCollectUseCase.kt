package com.lowe.wanandroid.services.usecase

import com.lowe.wanandroid.services.model.CollectEvent
import com.lowe.wanandroid.ui.collect.CollectRepository
import javax.inject.Inject

/**
 * 收藏操作UseCase
 */
class ArticleCollectUseCase @Inject constructor(private val collectRepository: CollectRepository) {

    suspend fun articleCollectAction(event: CollectEvent) =
        collectRepository.articleCollectAction(event)
}