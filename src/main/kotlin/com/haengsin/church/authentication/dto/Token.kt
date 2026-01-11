package com.haengsin.church.authentication.dto

import java.time.OffsetDateTime

data class Token(
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExpiresIn: Long,
    val refreshTokenExpiresIn: Long,
    val issuedAt: OffsetDateTime,
    ) {
    val expiredAt: OffsetDateTime
        get() = issuedAt.plusSeconds(accessTokenExpiresIn)
}