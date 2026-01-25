package com.haengsin.church.domain.board.service


import com.haengsin.church.domain.board.entity.Board
import com.haengsin.church.domain.board.vo.BoardListRequest
import com.haengsin.church.domain.board.vo.CreateBoardRequest
import com.haengsin.church.domain.board.vo.UpdateBoardRequest
import org.springframework.data.domain.Page

interface BoardService {

    fun createBoard(createBoardRequest: CreateBoardRequest): Board

    fun updateBoard(id: Long, updateBoardRequest: UpdateBoardRequest): Board

    fun deleteBoard(id: Long)

    fun getBoard(id: Long): Board

    fun getLatestBoard(): Board?

    fun getLatestBulletin(): Board

    fun getBoardList(boardListRequest: BoardListRequest):  Page<Board>
}