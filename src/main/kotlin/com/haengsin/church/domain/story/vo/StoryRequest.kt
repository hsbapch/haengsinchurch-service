package com.haengsin.church.domain.story.vo

import com.haengsin.church.domain.board.enums.ArticleType
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

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

data class GetStoryListRequest(
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
