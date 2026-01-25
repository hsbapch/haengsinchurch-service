package com.haengsin.church.domain.story.repository

import com.haengsin.church.domain.story.entity.QStory.story
import com.haengsin.church.domain.story.entity.Story
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class StoryRepositoryQuery(
    private val queryFactory: JPAQueryFactory
) {
    fun findByPagination(pageable: Pageable): Page<Story> {

        val content = queryFactory
            .selectFrom(story)
            .orderBy(story.createdAt.desc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val totalCount = queryFactory
            .select(story.id.count())
            .from(story)
            .fetchOne() ?: 0L

        return PageImpl(content, pageable, totalCount)
    }
}