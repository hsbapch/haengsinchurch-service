package com.haengsin.church.story.vo

import io.swagger.v3.oas.annotations.media.Schema

data class CreatePersonaRequest(
    @field:Schema(description = "페르소나 제목")
    val title: String,
    @field:Schema(description = "페르소나 이미지 URL")
    val imageUrl: String
)

data class UpdatePersonaRequest(
    @field:Schema(description = "페르소나 제목")
    val title: String,
    @field:Schema(description = "페르소나 이미지 URL")
    val imageUrl: String
)