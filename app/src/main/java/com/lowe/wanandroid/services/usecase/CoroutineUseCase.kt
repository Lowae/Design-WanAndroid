package com.lowe.wanandroid.services.usecase

import com.lowe.wanandroid.base.http.adapter.NetworkResponse
import com.lowe.wanandroid.result.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext


abstract class UseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {

    suspend operator fun invoke(parameters: P): Result<R> {
        return try {
            withContext(coroutineDispatcher) {
                execute(parameters).let {
                    Result.Success(it)
                }
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(parameters: P): R
}

abstract class FlowUseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {
    operator fun invoke(parameters: P): Flow<Result<R>> = execute(parameters)
        .catch { e -> emit(Result.Error(Exception(e))) }
        .flowOn(coroutineDispatcher)

    protected abstract fun execute(parameters: P): Flow<Result<R>>
}


abstract class NetWorkUseCase<in P, R : Any> {

    suspend operator fun invoke(parameters: P): NetworkResponse<R> {
        return try {
            execute(parameters)
        } catch (e: Exception) {
            NetworkResponse.UnknownError(e)
        }
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(parameters: P): NetworkResponse<R>
}
