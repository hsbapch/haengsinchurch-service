package com.haengsin.church.authentication.dto

import java.time.OffsetDateTime

data class AccessToken(
    val accessToken: String,
    val expiresIn: Long,
    val expiredAt: OffsetDateTime,
)