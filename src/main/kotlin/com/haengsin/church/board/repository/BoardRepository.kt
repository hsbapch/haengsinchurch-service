package com.haengsin.church.board.repository

import com.haengsin.church.board.entity.Board
import org.springframework.data.jpa.repository.JpaRepository

interface BoardRepository : JpaRepository<Board, Long> {

    fun findFirstByOrderByCreatedAtDesc(): Board?

}