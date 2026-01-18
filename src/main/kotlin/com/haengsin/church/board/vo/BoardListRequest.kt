package com.haengsin.church.board.vo

import com.haengsin.church.board.enums.ArticleType
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort


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

data class BoardListRequest(
    @field:Schema(description = "게시글 타입(공지사항, 주보, 목양걸럼, 간증)")
    val articleType: ArticleType?,
    @field:Schema(description = "페이지 번호", example = "1")
    val page: Int = 1,
    @field:Schema(description = "페이지 크기", example = "10")
    val size: Int = 10,
) {
    fun toPageable(sort: Sort = Sort.by(Sort.Direction.DESC, "createdAt")): Pageable {
        val pageIndex = (page - 1).coerceAtLeast(0)
        return PageRequest.of(pageIndex, size, sort)
    }
}
