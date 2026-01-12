package com.haengsin.church.board.exception

import com.haengsin.church.common.exception.NotFoundException

class BoardNotFoundException(id: Long) : NotFoundException(
    "id : $id / 게시글을 찾을 수 없습니다."
)