package com.haengsin.church.domain.ftp.service

import java.io.InputStream

interface FtpService {

    fun putObject(key: String, data: InputStream): String

    fun getObject(key: String): ByteArray
}