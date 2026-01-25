package com.haengsin.church.domain.board.usecase


import com.haengsin.church.common.Usecase
import com.haengsin.church.common.UsecaseInterface
import com.haengsin.church.common.dto.PageResponse
import com.haengsin.church.domain.board.entity.Board
import com.haengsin.church.domain.board.service.BoardService
import com.haengsin.church.domain.board.vo.BoardListRequest

@Usecase
class GetBoardListUsecase(
    private val boardService: BoardService,
) : UsecaseInterface<BoardListRequest, PageResponse<Board>> {

    override fun execute(input: BoardListRequest): PageResponse<Board> =
        boardService.getBoardList(input)
            .let(PageResponse.Companion::from)

}