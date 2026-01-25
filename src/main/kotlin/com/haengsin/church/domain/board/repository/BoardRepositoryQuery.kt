package com.haengsin.church.domain.board.repository


import com.haengsin.church.domain.board.entity.Board
import com.haengsin.church.domain.board.entity.QBoard.board
import com.haengsin.church.domain.board.enums.ArticleType
import com.haengsin.church.domain.board.vo.BoardListRequest
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page

import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class BoardRepositoryQuery(
    private val queryFactory: JPAQueryFactory
) {
    fun findByPagination(condition: BoardListRequest, pageable: Pageable): Page<Board> {

        val content = queryFactory
            .selectFrom(board)
            .where(
                articleTypeEq(condition.articleType)
            )
            .orderBy(board.createdAt.desc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val totalCount = queryFactory
            .select(board.id.count())
            .from(board)
            .where(
                articleTypeEq(condition.articleType)
            )
            .fetchOne() ?: 0L

        return PageImpl(content, pageable, totalCount)
    }

    private fun articleTypeEq(articleType: ArticleType?): BooleanExpression? =
        articleType?.let { board.articleType.eq(it) }

}