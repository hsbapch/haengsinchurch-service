package com.haengsin.church.authentication.expection

import com.haengsin.church.common.exception.NotFoundException

class UserNotFoundException(
    userId: String
) : NotFoundException(
    "아이디를 찾을 수 없습니다 : $userId"
)