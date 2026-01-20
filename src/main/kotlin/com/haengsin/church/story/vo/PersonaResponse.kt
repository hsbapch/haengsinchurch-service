package com.haengsin.church.story.vo

import io.swagger.v3.oas.annotations.media.Schema

data class PersonaResponse(
    @field:Schema(description = "페르소나 ID")
    val id: Long,
    @field:Schema(description = "페르소나 제목")
    val title: String,
    @field:Schema(description = "페르소나 이미지 URL")
    val imageUrl: String
)