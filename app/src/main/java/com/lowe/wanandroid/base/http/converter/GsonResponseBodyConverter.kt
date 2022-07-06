/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lowe.wanandroid.base.http.converter

import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.google.gson.TypeAdapter
import com.lowe.wanandroid.base.http.adapter.NetworkResponse
import okhttp3.ResponseBody
import retrofit2.Converter

class GsonResponseBodyConverter<T : Any>(
    private val gson: Gson,
    private val adapter: TypeAdapter<T>
) : Converter<ResponseBody, NetworkResponse<T>> {

    override fun convert(value: ResponseBody): NetworkResponse<T> {
        val jsonReader = gson.newJsonReader(value.charStream())
        value.use {
            jsonReader.beginObject()
            var errorCode = 0
            var errorMsg = ""
            var data: T? = null
            while (jsonReader.hasNext()) {
                when (jsonReader.nextName()) {
                    "errorCode" -> errorCode = jsonReader.nextInt()
                    "errorMsg" -> errorMsg = jsonReader.nextString()
                    "data" -> data = adapter.read(jsonReader)
                    else -> jsonReader.skipValue()
                }
            }
            jsonReader.endObject()
            return if (data == null) {
                NetworkResponse.UnknownError(JsonIOException("JSON document consumed failed"))
            } else if (errorCode != 0) {
                NetworkResponse.BizError(errorCode, errorMsg)
            } else {
                NetworkResponse.Success(data)
            }
        }
    }
}