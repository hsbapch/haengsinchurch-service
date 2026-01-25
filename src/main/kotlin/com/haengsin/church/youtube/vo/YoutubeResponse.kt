package com.haengsin.church.youtube.vo

import io.swagger.v3.oas.annotations.media.Schema
import java.time.OffsetDateTime

data class MainYoutubeResponse(
    @field:Schema(description = "Youtube 비디오 ID")
    val videoId: String,
    @field:Schema(description = "라이브 방송 여부")
    val isLive: Boolean
)
