package com.haengsin.church.board.exception

import com.haengsin.church.common.exception.NotFoundException

class BulletinNotFoundException() : NotFoundException(
    "가장 최근 주보가 없습니다"
)