package com.lowe.wanandroid.services.usecase

import com.lowe.wanandroid.services.repository.MessageRepository
import javax.inject.Inject

class UnreadMessageCountUseCase @Inject constructor(
    private val messageRepository: MessageRepository,
) : NetWorkUseCase<Unit, Int>() {

    override suspend fun execute(parameters: Unit) = messageRepository.getUnreadMessageCount()
}