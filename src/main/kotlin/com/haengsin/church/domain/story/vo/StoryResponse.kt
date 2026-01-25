package com.haengsin.church.domain.story.vo

import io.swagger.v3.oas.annotations.media.Schema


data class StoryResponse (
    @field:Schema(description = "스토리 제목")
    val title: String,
    @field:Schema(description = "스토리 내용 (HTML)")
    val content: String,
    @field:Schema(description = "페르소나 이미지 URL")
    val personaImageUrl: String,
)

data class StoryDetailResponse (
    @field:Schema(description = "스토리 제목")
    val title: String,
    @field:Schema(description = "스토리 내용 (HTML)")
    val content: String,
    @field:Schema(description = "페르소나 이미지 URL")
    val personaImageUrl: String,
)
