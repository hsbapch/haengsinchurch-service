package com.haengsin.church.domain.authentication.vo

import io.swagger.v3.oas.annotations.media.Schema
import java.time.OffsetDateTime

data class AuthenticationResponse(
    @field:Schema(description = "액세스 토큰 만료까지 남은 시간(초). 만료 전에 토큰을 갱신하기 위한 기준값")
    val expiresIn: Long,
    @field:Schema(description = "액세스 토큰 만료 시각(서버 기준)")
    val expiredAt: OffsetDateTime
)
