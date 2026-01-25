package com.haengsin.church.domain.board.usecase


import com.haengsin.church.common.Usecase
import com.haengsin.church.common.UsecaseInterface
import com.haengsin.church.domain.board.service.BoardService
import jakarta.transaction.Transactional

@Usecase
class DeleteBoardUsecase(
    private val boardService: BoardService,
) : UsecaseInterface<Long, Unit> {

    @Transactional
    override fun execute(input: Long) =
        boardService.deleteBoard(input)

}