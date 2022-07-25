package com.lowe.common.utils

import kotlinx.coroutines.channels.SendChannel

/**
 * 忽略任何异常
 */
fun <E> SendChannel<E>.tryOffer(element: E): Boolean = try {
    trySend(element).isSuccess
} catch (t: Throwable) {
    false // Ignore
}