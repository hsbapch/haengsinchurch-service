package com.haengsin.church.domain.board.service


import com.haengsin.church.domain.board.entity.Board
import com.haengsin.church.domain.board.exception.BoardNotFoundException
import com.haengsin.church.domain.board.exception.BulletinNotFoundException
import com.haengsin.church.domain.board.mapper.BoardMapper
import com.haengsin.church.domain.board.repository.BoardRepository
import com.haengsin.church.domain.board.repository.BoardRepositoryQuery
import com.haengsin.church.domain.board.vo.BoardListRequest
import com.haengsin.church.domain.board.vo.CreateBoardRequest
import com.haengsin.church.domain.board.vo.UpdateBoardRequest
import jakarta.transaction.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class BoardServiceImpl(
    private val boardRepository: BoardRepository,
    private val boardRepositoryQuery: BoardRepositoryQuery
) : BoardService {

    @Transactional
    override fun createBoard(
        createBoardRequest: CreateBoardRequest
    ): Board = createBoardRequest
        .let(BoardMapper::toBoard)
        .let(boardRepository::save)

    @Transactional
    override fun updateBoard(
        id: Long,
        updateBoardRequest: UpdateBoardRequest
    ): Board = getBoard(id)
        .apply { this.update(updateBoardRequest) }

    @Transactional
    override fun deleteBoard(id: Long) =
        boardRepository.deleteById(id)

    override fun getBoard(id: Long): Board =
        boardRepository.findByIdOrNull(id)
            ?: throw BoardNotFoundException(id)

    override fun getLatestBoard(): Board? =
        boardRepository.findFirstByOrderByCreatedAtDesc()

    override fun getLatestBulletin(): Board =
        boardRepository.findFirstByArticleTypeOrderByCreatedAtDesc()
            ?: throw BulletinNotFoundException()

    override fun getBoardList(boardListRequest: BoardListRequest): Page<Board> =
        boardRepositoryQuery.findByPagination(boardListRequest, boardListRequest.toPageable())


}