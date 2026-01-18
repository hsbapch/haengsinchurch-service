package com.haengsin.church.board.usecase

import com.haengsin.church.board.entity.Board
import com.haengsin.church.board.service.BoardService
import com.haengsin.church.board.vo.BoardListRequest
import com.haengsin.church.common.Usecase
import com.haengsin.church.common.UsecaseInterface
import com.haengsin.church.common.dto.PageResponse

@Usecase
class GetBoardListUsecase(
    private val boardService: BoardService,
) : UsecaseInterface<BoardListRequest, PageResponse<Board>> {

    override fun execute(input: BoardListRequest): PageResponse<Board> =
        boardService.getBoardList(input)
            .let(PageResponse.Companion::from)

}