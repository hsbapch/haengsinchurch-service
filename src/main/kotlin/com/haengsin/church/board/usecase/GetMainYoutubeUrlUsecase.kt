package com.haengsin.church.board.usecase

import com.haengsin.church.board.mapper.BoardMapper
import com.haengsin.church.board.service.BoardService
import com.haengsin.church.board.vo.BoardDetailResponse
import com.haengsin.church.common.Usecase
import com.haengsin.church.common.UsecaseInterface

@Usecase
class GetMainYoutubeUrlUsecase(
    private val boardService: BoardService,
): UsecaseInterface<Unit, String> {


    override fun execute(input: Unit): String =
        boardService.getLatestBoard()
            ?.youtubeUrl
            ?: ""

}