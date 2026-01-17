package com.haengsin.church.ftp.service

import java.io.InputStream

interface FtpService {

    fun putObject(key: String, data: InputStream): String

    fun getObject(key: String): ByteArray
}