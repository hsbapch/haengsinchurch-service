package com.haengsin.church.domain.authentication.expection

import com.haengsin.church.common.exception.NotFoundException

class UserNotFoundException(
    userId: String
) : NotFoundException(
    message = "User not found: $userId"
)