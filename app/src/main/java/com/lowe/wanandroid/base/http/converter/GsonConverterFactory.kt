package com.lowe.wanandroid.base.http.converter;

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Converter
import retrofit2.Retrofit
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
    ) = GsonResponseBodyConverter(gson, gson.getAdapter(TypeToken.get(type)))

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<out Annotation>,
        methodAnnotations: Array<out Annotation>,
        retrofit: Retrofit
    ) = GsonRequestBodyConverter(gson, gson.getAdapter(TypeToken.get(type)))
}
