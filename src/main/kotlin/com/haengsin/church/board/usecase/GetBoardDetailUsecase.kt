package com.haengsin.church.board.usecase

import com.haengsin.church.board.mapper.BoardMapper
import com.haengsin.church.board.service.BoardService
import com.haengsin.church.board.vo.BoardDetailResponse
import com.haengsin.church.common.Usecase
import com.haengsin.church.common.UsecaseInterface

@Usecase
class GetBoardDetailUsecase(
    private val boardService: BoardService,
): UsecaseInterface<Long, BoardDetailResponse> {


    override fun execute(input: Long): BoardDetailResponse =
        boardService.getBoard(input)
            .let(BoardMapper::toBoardDetailResponse)
}