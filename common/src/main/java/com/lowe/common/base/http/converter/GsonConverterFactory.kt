package com.lowe.common.base.http.converter

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class GsonConverterFactory(private val gson: Gson) : Converter.Factory() {

    companion object {

        fun create(): GsonConverterFactory {
            return create(Gson())
        }

        private fun create(gson: Gson?): GsonConverterFactory {
            if (gson == null) throw NullPointerException("gson == null")
            return GsonConverterFactory(gson)
        }
    }

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): GsonResponseBodyConverter<out Any> {
        check(type is ParameterizedType) {
            "type must be parameterized as Call<NetworkResponse<<Foo>> or Call<NetworkResponse<out Foo>>"
        }
        return GsonResponseBodyConverter(
            gson,
            /**
             * 获取NetWorkResponse包装内的第一个泛型，如NetWorkResponse<List<Article>>获取List<Article>以让Gson成功解析
             */
            gson.getAdapter(TypeToken.get(getParameterUpperBound(0, type)))
        )
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<out Annotation>,
        methodAnnotations: Array<out Annotation>,
        retrofit: Retrofit
    ) = GsonRequestBodyConverter(gson, gson.getAdapter(TypeToken.get(type)))
}
