package com.haengsin.church.common.dto

import org.springframework.data.domain.Page

data class PageResponse<T>(
    val page: Int,
    val size: Int,
    val totalElements: Long,
    val totalPages: Int,
    val hasNext: Boolean,
    val content: List<T>
) {

    companion object {
        fun <T : Any> from(page: Page<T>): PageResponse<T> =
            PageResponse(
                page = page.number + 1,
                size = page.size,
                totalElements = page.totalElements,
                totalPages = page.totalPages,
                hasNext = page.hasNext(),
                content = page.content
            )
    }
}