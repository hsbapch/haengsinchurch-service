package com.haengsin.church.domain.board.usecase


import com.haengsin.church.common.Usecase
import com.haengsin.church.common.UsecaseInterface
import com.haengsin.church.domain.board.mapper.BoardMapper
import com.haengsin.church.domain.board.service.BoardService
import com.haengsin.church.domain.board.vo.BoardDetailResponse

@Usecase
class GetBoardDetailUsecase(
    private val boardService: BoardService,
): UsecaseInterface<Long, BoardDetailResponse> {


    override fun execute(input: Long): BoardDetailResponse =
        boardService.getBoard(input)
            .let(BoardMapper::toBoardDetailResponse)
}