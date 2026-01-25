package com.haengsin.church.youtube.dto

import java.time.OffsetDateTime

data class YoutubePlayListDto(
    val title: String,
    val description: String,
    val videoId: String,
    val publishedAt: OffsetDateTime,
)
