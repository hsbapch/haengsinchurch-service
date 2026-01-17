package com.haengsin.church.board.api

import com.haengsin.church.board.usecase.GetBoardDetailUsecase
import com.haengsin.church.board.usecase.GetMainYoutubeUrlUsecase
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Admin Board", description = "게시판")
@RestController
@RequestMapping("/api/v1/boards")
class BoardController(
    private val getBoardDetailUsecase: GetBoardDetailUsecase,
    private val getMainYoutubeUrlUsecase: GetMainYoutubeUrlUsecase,
) {

    @GetMapping("/{id}")
    fun getBoardDetail(
        @PathVariable id: Long,
    ) = getBoardDetailUsecase.execute(id)

    @GetMapping("/main/youtube-url")
    fun getMainYoutubeUrl() =
        getMainYoutubeUrlUsecase.execute(Unit)


}