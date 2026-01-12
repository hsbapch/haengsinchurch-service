package com.haengsin.church.board.service

import com.haengsin.church.board.entity.Board
import com.haengsin.church.board.vo.CreateBoardRequest
import com.haengsin.church.board.vo.UpdateBoardRequest

interface BoardService {

    fun createBoard(createBoardRequest: CreateBoardRequest): Board

    fun updateBoard(id: Long, updateBoardRequest: UpdateBoardRequest): Board

    fun deleteBoard(id: Long)

    fun getBoard(id: Long): Board

    fun getLatestBoard(): Board?
}