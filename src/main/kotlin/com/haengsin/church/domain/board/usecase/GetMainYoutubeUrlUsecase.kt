package com.haengsin.church.domain.board.usecase


import com.haengsin.church.common.Usecase
import com.haengsin.church.common.UsecaseInterface
import com.haengsin.church.domain.board.service.BoardService

@Usecase
class GetMainYoutubeUrlUsecase(
    private val boardService: BoardService,
): UsecaseInterface<Unit, String> {


    override fun execute(input: Unit): String =
        boardService.getLatestBoard()
            ?.youtubeUrl
            ?: ""

}