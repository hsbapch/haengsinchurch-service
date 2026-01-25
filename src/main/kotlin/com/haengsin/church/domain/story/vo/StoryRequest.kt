package com.haengsin.church.domain.story.vo

import io.swagger.v3.oas.annotations.media.Schema

data class CreateStoryRequest(
    @field:Schema(description = "스토리 제목")
    val title: String,
    @field:Schema(description = "스토리 내용")
    val content: String,
    @field:Schema(description = "페르소나 ID")
    val personaId: Long,
)

data class UpdateStoryRequest(
    @field:Schema(description = "스토리 제목")
    val title: String,
    @field:Schema(description = "스토리 내용")
    val content: String,
    @field:Schema(description = "페르소나 ID")
    val personaId: Long,
)