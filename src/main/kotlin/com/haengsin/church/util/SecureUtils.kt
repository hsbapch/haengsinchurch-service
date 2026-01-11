package com.haengsin.church.util

import io.jsonwebtoken.security.Keys
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import javax.crypto.SecretKey

object SecureUtils {

    fun toHmacShaKey(secret: String): SecretKey =
        MessageDigest.getInstance("SHA-256")
            .digest(secret.toByteArray(StandardCharsets.UTF_8))
            .let(Keys::hmacShaKeyFor)


}