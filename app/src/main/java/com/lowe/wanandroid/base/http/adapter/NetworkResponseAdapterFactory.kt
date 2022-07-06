package com.lowe.wanandroid.base.http.adapter

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class NetworkResponseAdapterFactory(
    private val errorHandler: ErrorHandler? = null
) : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {

        // suspend 函数在 Retrofit 中的返回值其实是 `Call`
        if (Call::class.java != getRawType(returnType)) return null

        // 检查返回类型是否为 `ParameterizedType`
        check(returnType is ParameterizedType) {
            "return type must be parameterized as Call<NetworkResponse<<Foo>> or Call<NetworkResponse<out Foo>>"
        }

        // 获取Call内的一层泛型类型
        val responseType = getParameterUpperBound(0, returnType)

        // 如果非NetworkResponse不处理
        if (getRawType(responseType) != NetworkResponse::class.java) return null

        return NetworkResponseAdapter(responseType, errorHandler)
    }
}
