package com.lowe.common.services.usecase

import com.lowe.common.services.model.CollectEvent
import com.lowe.common.services.repository.CollectRepository
import javax.inject.Inject

/**
 * 收藏操作UseCase
 */
class ArticleCollectUseCase @Inject constructor(private val collectRepository: CollectRepository) {

    suspend fun articleCollectAction(event: CollectEvent) =
        collectRepository.articleCollectAction(event)
}