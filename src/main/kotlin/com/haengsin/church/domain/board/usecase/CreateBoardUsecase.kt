package com.haengsin.church.domain.board.usecase


import com.haengsin.church.common.Usecase
import com.haengsin.church.common.UsecaseInterface
import com.haengsin.church.domain.board.service.BoardService
import com.haengsin.church.domain.board.vo.CreateBoardRequest
import jakarta.transaction.Transactional

@Usecase
class CreateBoardUsecase(
    private val boardService: BoardService,
) : UsecaseInterface<CreateBoardRequest, Unit> {

    @Transactional
    override fun execute(input: CreateBoardRequest) {
        boardService.createBoard(input)
    }
}