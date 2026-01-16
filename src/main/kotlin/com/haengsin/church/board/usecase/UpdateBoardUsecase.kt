package com.haengsin.church.board.usecase

import com.haengsin.church.board.mapper.BoardMapper
import com.haengsin.church.board.service.BoardService
import com.haengsin.church.board.vo.BoardDetailResponse
import com.haengsin.church.board.vo.CreateBoardRequest
import com.haengsin.church.board.vo.UpdateBoardRequest
import com.haengsin.church.common.Usecase
import com.haengsin.church.common.UsecaseInterface
import jakarta.transaction.Transactional

@Usecase
class UpdateBoardUsecase(
    private val boardService: BoardService,
) : UsecaseInterface<UpdateBoardUsecase.WrappedRequest, BoardDetailResponse> {

    data class WrappedRequest(
        val id: Long,
        val request: UpdateBoardRequest
    )

    @Transactional
    override fun execute(input: WrappedRequest): BoardDetailResponse =
        boardService.updateBoard(input.id, input.request)
            .let(BoardMapper::toBoardDetailResponse)

}