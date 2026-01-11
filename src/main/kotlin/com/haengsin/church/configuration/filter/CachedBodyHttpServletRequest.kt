package com.haengsin.church.configuration.filter

import org.springframework.util.StreamUtils
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStreamReader
import jakarta.servlet.ServletInputStream
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletRequestWrapper


class CachedBodyHttpServletRequest(
    request: HttpServletRequest
) : HttpServletRequestWrapper(request) {

    private val cachedBody: ByteArray

    init {
        val requestInputStream = request.inputStream
        cachedBody = StreamUtils.copyToByteArray(requestInputStream)
    }

    @Throws(IOException::class)
    override fun getInputStream(): ServletInputStream {
        return CachedBodyServletInputStream(cachedBody)
    }

    @Throws(IOException::class)
    override fun getReader(): BufferedReader {
        val byteArrayInputStream = ByteArrayInputStream(cachedBody)
        return BufferedReader(InputStreamReader(byteArrayInputStream))
    }
}