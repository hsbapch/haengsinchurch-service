package com.haengsin.church.board.usecase

import com.haengsin.church.board.service.BoardService
import com.haengsin.church.board.vo.CreateBoardRequest
import com.haengsin.church.common.Usecase
import com.haengsin.church.common.UsecaseInterface
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