package com.haengsin.church.board.api

import com.haengsin.church.board.usecase.CreateBoardUsecase
import com.haengsin.church.board.usecase.DeleteBoardUsecase
import com.haengsin.church.board.usecase.UpdateBoardUsecase
import com.haengsin.church.board.vo.CreateBoardRequest
import com.haengsin.church.board.vo.UpdateBoardRequest
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin/api/v1/boards")
class AdminBoardController(
    private val createBoardUsecase: CreateBoardUsecase,
    private val updateBoardUsecase: UpdateBoardUsecase,
    private val deleteBoardUsecase: DeleteBoardUsecase,
) {

    @PostMapping
    fun createBoard(
        @RequestBody request: CreateBoardRequest
    ) = createBoardUsecase.execute(request)

    @PutMapping("/{id}")
    fun createBoard(
        @PathVariable id: Long,
        @RequestBody request: UpdateBoardRequest
    ) = updateBoardUsecase.execute(
        UpdateBoardUsecase.WrappedRequest(id, request)
    )

    @DeleteMapping("/{id}")
    fun deleteBoard(
        @PathVariable id: Long,
    ) = deleteBoardUsecase.execute(id)

}