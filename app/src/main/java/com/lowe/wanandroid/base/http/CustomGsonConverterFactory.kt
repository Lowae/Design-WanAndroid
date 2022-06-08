package com.lowe.wanandroid.base.http

import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonToken
import com.lowe.wanandroid.base.AppLog
import com.lowe.wanandroid.services.ApiResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import okio.Buffer
import retrofit2.Converter
import retrofit2.Retrofit
import java.io.ByteArrayInputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.lang.reflect.Type
import java.nio.charset.Charset
import kotlin.text.Charsets.UTF_8

class CustomGsonConverterFactory(private val gson: Gson) : Converter.Factory() {
    companion object {
        fun create(): CustomGsonConverterFactory {
            return create(Gson())
        }

        private fun create(gson: Gson?): CustomGsonConverterFactory {
            if (gson == null) throw NullPointerException("gson == null")
            return CustomGsonConverterFactory(gson)
        }
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<out Annotation>,
        methodAnnotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        return CustomGsonRequestBodyConverter(gson, gson.getAdapter(TypeToken.get(type)))
    }

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        return CustomGsonResponseBodyConverter(gson, gson.getAdapter(TypeToken.get(type)))
    }
}

class CustomGsonRequestBodyConverter<T>(
    private val gson: Gson,
    private val adapter: TypeAdapter<T>
) : Converter<T, RequestBody> {
    private val MEDIA_TYPE = "application/json; charset=UTF-8".toMediaTypeOrNull()
    private val UTF_8 = Charset.forName("UTF-8")

    override fun convert(value: T): RequestBody {
        AppLog.d(msg = "CustomGsonRequestBodyConverter")
        val buffer = Buffer()
        val writer = OutputStreamWriter(buffer.outputStream(), UTF_8)
        val jsonWriter = gson.newJsonWriter(writer)
        adapter.write(jsonWriter, value)
        jsonWriter.close()
        return buffer.readByteString().toRequestBody(MEDIA_TYPE)
    }
}

class CustomGsonResponseBodyConverter<T>(
    private val gson: Gson,
    private val adapter: TypeAdapter<T>
) :
    Converter<ResponseBody, T> {
    override fun convert(value: ResponseBody): T {
        AppLog.d(msg = "CustomGsonResponseBodyConverter")
        val response = value.string()
        val httpStatus = gson.fromJson(response, ApiResponse::class.java)
        /** 先将code与msg解析出来，code非0的情况下直接抛ApiException异常，这样我们就将这种异常交给onFailure()处理了**/
        if (httpStatus.errorCode != 0) {
            value.close()
            throw ApiException(httpStatus.errorCode, httpStatus.errorMsg)
        }
        val contentType = value.contentType()
        val charset = contentType?.charset(UTF_8) ?: UTF_8
        val inputStream = ByteArrayInputStream(response.toByteArray())
        val reader = InputStreamReader(inputStream, charset!!)
        val jsonReader = gson.newJsonReader(reader)

        value.use { _ ->
            val result = adapter.read(jsonReader)
            if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
                throw JsonIOException("JSON document was not fully consumed.")
            }
            return result
        }
    }
}

class ApiException(var code: Int, override var message: String?) : RuntimeException(message) {
    fun code(): Int {
        return code
    }

    fun message(): String? {
        return message
    }
}