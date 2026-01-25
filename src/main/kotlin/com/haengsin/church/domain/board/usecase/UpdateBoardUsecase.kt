package com.haengsin.church.domain.board.usecase


import com.haengsin.church.common.Usecase
import com.haengsin.church.common.UsecaseInterface
import com.haengsin.church.domain.board.mapper.BoardMapper
import com.haengsin.church.domain.board.service.BoardService
import com.haengsin.church.domain.board.vo.BoardDetailResponse
import com.haengsin.church.domain.board.vo.UpdateBoardRequest
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