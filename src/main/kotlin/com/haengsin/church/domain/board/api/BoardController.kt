package com.haengsin.church.domain.board.api


import com.haengsin.church.common.dto.PageResponse
import com.haengsin.church.domain.board.entity.Board
import com.haengsin.church.domain.board.usecase.GetBoardDetailUsecase
import com.haengsin.church.domain.board.usecase.GetBoardListUsecase
import com.haengsin.church.domain.board.usecase.GetMainYoutubeUrlUsecase
import com.haengsin.church.domain.board.vo.BoardListRequest
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Board", description = "게시판")
@RestController
@RequestMapping("/api/v1/boards")
class BoardController(
    private val getBoardDetailUsecase: GetBoardDetailUsecase,
    private val getMainYoutubeUrlUsecase: GetMainYoutubeUrlUsecase,

    private val getBoardListUsecase: GetBoardListUsecase,
) {

    @GetMapping("/{id}")
    fun getBoardDetail(
        @PathVariable id: Long,
    ) = getBoardDetailUsecase.execute(id)

    @GetMapping("/main/youtube-url")
    fun getMainYoutubeUrl() =
        getMainYoutubeUrlUsecase.execute(Unit)


    @GetMapping("/list")
    fun getBoards(
        @ModelAttribute boardListRequest: BoardListRequest
    ): PageResponse<Board> = getBoardListUsecase.execute(boardListRequest)


}