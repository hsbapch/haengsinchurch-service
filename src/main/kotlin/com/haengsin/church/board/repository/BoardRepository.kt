package com.haengsin.church.board.repository

import com.haengsin.church.board.entity.Board
import com.haengsin.church.board.enums.ArticleType
import org.springframework.data.jpa.repository.JpaRepository

interface BoardRepository : JpaRepository<Board, Long> {

    fun findFirstByOrderByCreatedAtDesc(): Board?

    fun findFirstByArticleTypeOrderByCreatedAtDesc(articleType: ArticleType = ArticleType.BULLETIN): Board?

}