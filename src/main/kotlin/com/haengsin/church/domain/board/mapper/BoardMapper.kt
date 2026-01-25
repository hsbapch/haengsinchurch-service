package com.haengsin.church.domain.board.mapper

import com.haengsin.church.domain.board.entity.Board
import com.haengsin.church.domain.board.vo.BoardDetailResponse
import com.haengsin.church.domain.board.vo.CreateBoardRequest


object BoardMapper {

    fun toBoard(boardRequest: CreateBoardRequest): Board =
        Board(
            title = boardRequest.title,
            content = boardRequest.content,
            articleType = boardRequest.articleType,
            youtubeUrl = boardRequest.youtubeUrl,
        )


    fun toBoardDetailResponse(board: Board) =
        BoardDetailResponse(
            id = board.id,
            title = board.title,
            content = board.content,
            articleType = board.articleType,
            youtubeUrl = board.youtubeUrl,
            createdAt = board.createdAt,
            updatedAt = board.updatedAt
        )
}