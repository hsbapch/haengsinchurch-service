package com.haengsin.church.configuration.filter

import java.io.ByteArrayInputStream
import java.io.InputStream
import jakarta.servlet.ReadListener
import jakarta.servlet.ServletInputStream

class CachedBodyServletInputStream(cachedBody: ByteArray) : ServletInputStream() {

    private val cachedBodyInputStream: InputStream = ByteArrayInputStream(cachedBody)

    override fun read(): Int {
        return cachedBodyInputStream.read()
    }

    override fun isFinished(): Boolean {
        return cachedBodyInputStream.available() == 0
    }

    override fun isReady(): Boolean {
        return true
    }

    override fun setReadListener(listener: ReadListener?) {
        throw UnsupportedOperationException()
    }
}
