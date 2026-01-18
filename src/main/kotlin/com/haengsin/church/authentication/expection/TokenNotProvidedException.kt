package com.haengsin.church.authentication.expection

import com.haengsin.church.common.exception.AccessDeniedException

class TokenNotProvidedException(): AccessDeniedException(
    message = "Token not provided",
)