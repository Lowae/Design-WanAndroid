package com.lowe.common.base.http.cookie

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*
import okhttp3.Cookie

object CookieSerializer : KSerializer<Cookie> {

    override fun deserialize(decoder: Decoder): Cookie {
        return decoder.decodeStructure(descriptor) {
            var name = ""
            var value = ""
            var expiresAt = 0L
            var domain = ""
            var path = "/"
            var secure = false
            var httpOnly = false
            var persistent = false
            var hostOnly = false
            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> name = decodeStringElement(descriptor, index)
                    1 -> value = decodeStringElement(descriptor, index)
                    2 -> expiresAt = decodeLongElement(descriptor, index)
                    3 -> domain = decodeStringElement(descriptor, index)
                    4 -> path = decodeStringElement(descriptor, index)
                    5 -> secure = decodeBooleanElement(descriptor, index)
                    6 -> httpOnly = decodeBooleanElement(descriptor, index)
                    7 -> persistent = decodeBooleanElement(descriptor, index)
                    8 -> hostOnly = decodeBooleanElement(descriptor, index)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }
            Cookie.Builder().name(name).value(value).expiresAt(expiresAt).path(path)
                .apply {
                    if (hostOnly) hostOnlyDomain(domain) else domain(domain)
                    if (secure) secure()
                    if (httpOnly) httpOnly()
                }
                .build()
        }
    }

    override val descriptor: SerialDescriptor
        get() = buildClassSerialDescriptor("Cookie") {
            element<String>("name")
            element<String>("value")
            element<Long>("expiresAt")
            element<String>("domain")
            element<String>("path")
            element<Boolean>("secure")
            element<Boolean>("httpOnly")
            element<Boolean>("persistent")
            element<Boolean>("hostOnly")
        }

    override fun serialize(encoder: Encoder, value: Cookie) {
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.name)
            encodeStringElement(descriptor, 1, value.value)
            encodeLongElement(descriptor, 2, value.expiresAt)
            encodeStringElement(descriptor, 3, value.domain)
            encodeStringElement(descriptor, 4, value.path)
            encodeBooleanElement(descriptor, 5, value.secure)
            encodeBooleanElement(descriptor, 6, value.httpOnly)
            encodeBooleanElement(descriptor, 7, value.persistent)
            encodeBooleanElement(descriptor, 8, value.hostOnly)
        }
    }
}