package com.haengsin.church.board.vo

import com.haengsin.church.board.enum.ArticleType
import io.swagger.v3.oas.annotations.media.Schema

data class CreateBoardRequest(
    @field:Schema(description = "게시글 제목")
    val title: String,
    @field:Schema(description = "게시글 내용 (Markdown)")
    val content: String,
    @field:Schema(description = "Youtube URL")
    val youtubeUrl: String?,
    @field:Schema(description = "게시글 타입(공지사항, 주보, 목양걸럼, 간증)")
    val articleType: ArticleType,
)

data class UpdateBoardRequest(
    @field:Schema(description = "게시글 제목")
    val title: String,
    @field:Schema(description = "게시글 내용 (Markdown)")
    val content: String,
    @field:Schema(description = "Youtube URL")
    val youtubeUrl: String?,
    @field:Schema(description = "게시글 타입(공지사항, 주보, 목양걸럼, 간증)")
    val articleType: ArticleType,
)