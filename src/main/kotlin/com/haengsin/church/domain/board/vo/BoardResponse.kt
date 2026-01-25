package com.haengsin.church.domain.board.vo


import com.haengsin.church.domain.board.enums.ArticleType
import io.swagger.v3.oas.annotations.media.Schema
import java.time.OffsetDateTime

data class BoardDetailResponse(
    @field:Schema(description = "게시글 id")
    val id: Long,
    @field:Schema(description = "게시글 제목")
    val title: String,
    @field:Schema(description = "게시글 내용 (Markdown)")
    val content: String,
    @field:Schema(description = "Youtube URL")
    val youtubeUrl: String?,
    @field:Schema(description = "게시글 타입(공지사항, 주보, 목양걸럼, 간증)")
    val articleType: ArticleType,
    @field:Schema(description = "게시글 생성일자")
    val createdAt: OffsetDateTime,
    @field:Schema(description = "게시글 수정일자")
    val updatedAt: OffsetDateTime
)