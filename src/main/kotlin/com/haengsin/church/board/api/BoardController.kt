package com.haengsin.church.board.api

import com.haengsin.church.board.usecase.GetBoardDetailUsecase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/boards")
class BoardController(
    private val getBoardDetailUsecase: GetBoardDetailUsecase,
) {

    @GetMapping("/{id}")
    fun getBoardDetail(
        @PathVariable id: Long,
    ) = getBoardDetailUsecase.execute(id)


}