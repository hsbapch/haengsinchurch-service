package com.haengsin.church.authentication.expection

import com.haengsin.church.common.exception.AccessDeniedException

class TokenNotProvidedException(): AccessDeniedException(
    message = "인증 토큰이 제공되지 않았습니다"
)